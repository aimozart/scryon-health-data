# Scryon Health Data: event specification (v1)

An evidence event is a signed JSON object. The node validates, canonicalises and signs it. This page
describes the fields, the closed vocabularies and the control mapping. All examples are synthetic.

## Event kinds

| `kind` | What it records | Allowed `label` values |
|---|---|---|
| `evidence.pipeline_run` | One layer's run for one night | `bronze`, `silver`, `gold`, `platinum` |
| `evidence.control_check` | A security or data-safety check | `rls_test`, `masking_check`, `k_anonymity`, `canary_scan`, `identifier_range`, `secret_scan`, `ci_tests` |
| `evidence.access` | A data access decision | `table_read` |
| `evidence.change` | A change | `deploy`, `rollback`, `schema_change`, `key_rotation` |
| `evidence.policy` | A policy event | `policy_approved` |

A label that does not belong to its kind is refused.

## Fields

| Field | Meaning |
|---|---|
| `v`, `type`, `synthetic` | Format version (1), event type, always `true` in this project |
| `kind`, `label` | See above |
| `decision` | `allow` = passed, `deny` = failed, `info` = recorded (other codes exist for AI decisions) |
| `reason` | Closed code, for example `check_passed`, `check_failed` |
| `evidence.control` | The control supported (see the table below) |
| `evidence.total` / `passed` / `flagged` | Items examined / passed or returned / flagged, quarantined or masked. `passed` and `flagged` never exceed `total` |
| `subject_hash` | `BLAKE3("entropa-evidence-subject-v1" 0x00 ‖ frame(detail) ‖ frame(run_id))`, each part framed by an 8-byte big-endian length. The detail text itself is dropped |
| `ts`, `key_id`, `handle`, `tenant` | Unix seconds, signer key id (16 hex), public handle, site |
| `sig` | ML-DSA-65 signature (hex) over the digest of the canonical bytes |

The counts mean: **run** = input rows, valid rows, quarantined rows; **check** = tests run, passed, failed;
**access** = rows requested, returned, masked.

## Data minimisation

- Events carry codes, counts and one hash. There is no free-text field and no row data.
- Detail text containing an identifier shape in a valid range (for example a nine-digit number laid out as an
  SSN in an area that can be issued) is **refused** before anything is hashed. Reserved invalid ranges are accepted.
- The detail is hashed with the run id so it can be proven later without being stored.

## Control codes

Control names only; no framework text is reproduced.

| Code | Supports |
|---|---|
| `hipaa_164_308_a1` | HIPAA Security Rule: security management process |
| `hipaa_164_308_a4` | information access management |
| `hipaa_164_312_a1` | access control |
| `hipaa_164_312_b` | audit controls |
| `hipaa_164_312_c1` | integrity |
| `hipaa_164_312_d` | person or entity authentication |
| `hipaa_164_312_e1` | transmission security |
| `hitrust_01_access_control` | HITRUST category: access control |
| `hitrust_06_compliance` | compliance |
| `hitrust_09_operations` | communications and operations management |
| `hitrust_10_sdlc` | systems acquisition, development and maintenance |
| `hitrust_13_privacy` | privacy practices |
| `policy_p0_data_safety` | our policy P0: data safety |
| `policy_p3_separation_of_duties` | our policy P3: separation of duties |

## Example controls to evidence

| Control | Typical evidence |
|---|---|
| `hipaa_164_312_b` audit controls | pipeline runs and access decisions, signed and time-stamped |
| `hipaa_164_312_c1` integrity | valid + quarantine = input; hash-chain checks |
| `hipaa_164_312_a1` access control | row-level security tests (the wrong user sees zero rows), access decisions by role |
| `hitrust_13_privacy` | masking, k-anonymity and leak-scan results |
| `hitrust_10_sdlc` | CI tests, secret scans, deploys and rollbacks |

## A sample event

See [examples/sample-event.json](examples/sample-event.json) (synthetic; signed with a public demo key; the
signature is abbreviated in the file).

## Verifying

Recompute the digest from the canonical bytes (keys sorted, no whitespace, no `sig`), then verify `sig` with the
node's public key and any conforming ML-DSA-65 (FIPS 204) implementation. Check each checkpoint (Merkle root, size and
timestamp, signed) and the leaf hashes with the rules in <https://docs.entropa.space/hashes-and-logs>. The explorer's "Verify in your
browser" button does all of this with a self-contained script and only the public key.

## Limits

Evidence proves what was recorded, not what was omitted. Mapped to the controls above; not an assessment.
