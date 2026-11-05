# Scryon Health Data

**Audit evidence for healthcare data engineering, captured as the work happens.**

Every pipeline run, control check, access decision and change is signed into a tamper-evident ledger and
filed under the HIPAA or HITRUST control it supports. Auditors verify it themselves. **No PHI, ever.**
Part of [Entropa](https://entropa.space). Site and diagram: <https://entropa.space/scryon-health-data>.

> All data in this project is **synthetic**. Wording: *aligned with and mapped to* HIPAA and HITRUST
> controls. Not independently assessed. Not certified. Evidence is not compliance.

## Why

Audit preparation is usually a scramble: screenshots, exports, spreadsheets assembled months after the fact.
Scryon Health Data records evidence **continuously, as the work happens**:

| You get | How |
|---|---|
| Evidence on demand | Each run, check, access and change is a signed, time-stamped event filed under a control |
| Controls watched continuously | A failed check or broken invariant is a signed record the moment it happens |
| Tamper evidence | Events are signed (ML-DSA-65) and chained into a Merkle log; verify with the public key alone |
| No PHI by design | Events hold counts, closed codes and a hash of the detail. Identifier-shaped text is refused |
| One truth for engineers and auditors | The ledger that shows last night's quarantine count also shows the integrity-control evidence |

## How it works

```
 pipeline runs ─┐
 control checks ─┤
 access decisions ─┼─►  Evidence node  ─►  Append-only ledger  ─►  Scryon Health Data explorer
 changes, deploys ─┤    validate, refuse        Merkle log,            (evidence by control)
 policy events ─┘      PHI-shaped data,        signed checkpoints         and any auditor who
                       hash the detail,                                   verifies the signature
                       add the control code,
                       sign (ML-DSA-65)
```

See [SPEC.md](SPEC.md) for the event format, the closed vocabularies and the control mapping, and
[examples/](examples/) for emitters (shell, Python, Scala) and a real synthetic sample event.

## Status

| Piece | Status |
|---|---|
| Event types, closed vocabularies and control codes | **Built** and tested |
| Signing and verification | **Built** (shared with the Entropa ledger) |
| Evidence endpoint on the node (`POST /v1/evidence`) | Next |
| Pipeline, CI and deploy emitters | Examples here; first-party emitters next |
| Scryon Health Data explorer (evidence by control) | Planned |

## Free, and how to get it

- **This repository** (specification, examples, documentation): MIT licence, free to use and adapt.
- **The node**: a free binary or container you run yourself, in the cloud or on-premises. You keep the key
  and the data. The node's source code is proprietary and is not published. Binary releases will be announced
  on the site; licence terms are published with each release.
- **The hosted explorer**: free for now, while it is funded by cloud credits. It may later stop or carry a small
  per-event charge, with notice on the site first.

## Honest limits

The ledger proves recorded events were not altered. It cannot prove nothing was left out, so the emitters are
part of your controls. It supports your own compliance program; it does not replace it.

## Contributors

See [CONTRIBUTORS.md](CONTRIBUTORS.md). Security issues: see [SECURITY.md](SECURITY.md).
