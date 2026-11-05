# Independent AI-assisted readiness assessment: HIPAA Security Rule safeguards

**Label (read first):** an *independent AI-assisted readiness assessment against the HIPAA Security Rule safeguards*. It is **not** a HIPAA audit, **not** a HITRUST assessment and **not** a certification. All data in the system is synthetic.

## Scope and method
- **Scope:** the synthetic-data healthcare data platform, the Entropa evidence ledger and its sites; 15 safeguards of 45 CFR 164 Subpart C (administrative, physical, technical), by their public regulation headings.
- **Auditor:** Gemini 3.8 Flash (a different model family from the builder, a Claude model), read-only: it receives a data pack and has no tools. It rates each safeguard evidenced, partial, gap, inherited or not applicable, citing the evidence shown.
- **Sampling:** the whole evidence ledger (round 1: 77 records; round 3: 102), summarised by control, check and outcome, plus policy titles and operator-stated system facts.
- **Loop:** baseline (round 1) → the builder remediates → the auditor retests (rounds 2 and 3). The auditor never fixes or approves its own findings; the Human Principal decides.
- **Method change, disclosed:** rounds 1 and 2 were a **single** model run each. A third-round single run rated two safeguards lower for reasons unrelated to any change in evidence, which showed that one run is noisy. Round 3 therefore takes the **consensus of 3 runs** (ties go to the more conservative rating) and flags any safeguard where the runs disagreed. Rounds 1 and 2 are not strictly comparable to round 3 for that reason.

## Round 1 (baseline, single run): {'evidenced': 5, 'partial': 7, 'gap': 2, 'inherited': 1}
| Safeguard | Rating | Severity | Finding |
|---|---|---|---|
| 164.308(a)(1) | gap | high | No formal risk analysis document exists, and formal risk management review procedures have not been conducted despite the existence of Policy P7. |
| 164.308(a)(2) | partial | medium | The Human Principal establishes and approves policies, but formal documentation explicitly designating a Security Official and defining statutory resp |
| 164.308(a)(3) | partial | medium | Roles are separated by technical identities and approval gates, but formal workforce screening, supervision, and termination procedures are not docume |
| 164.308(a)(4) | partial | medium | Automated CI tests enforce access control baselines, but formal access authorization, review, and modification workflows are not documented. |
| 164.308(a)(5) | gap | medium | No security awareness training records exist for the workforce despite the presence of Policy P12. |
| 164.308(a)(6) | partial | medium | Incident response policy exists (P6), but no operational incident response drills or test records have been executed. |
| 164.308(a)(7) | partial | high | Data and cryptographic keys are mirrored to an encrypted cloud drive and private Git server, but no data restoration or disaster recovery tests have b |
| 164.308(a)(8) | partial | medium | Automated technical evaluations run in CI/CD, but periodic comprehensive technical and non-technical HIPAA security evaluations have not been conducte |
| 164.310(a) | inherited | - | Physical data center facilities are provided by Google Cloud, AWS, and Azure under the shared responsibility model. |
| 164.310(d) | partial | medium | Cloud media handling is governed by cloud providers and policies, but formal accountability and disposal controls for operator endpoints are not evide |
| 164.312(a) | evidenced | - | Unique technical identities, private cloud identity tokens, scale-to-zero compute isolation, and cryptographic signing keys are enforced and verified |
| 164.312(b) | evidenced | - | An append-only Merkle ledger records operational activity, code deployments, and policy checks, secured with multi-cloud witness co-signing. |
| 164.312(c) | evidenced | - | Data integrity is ensured through signed Merkle tree checkpoints, managed KMS ML-DSA-65 keys, multi-cloud witness signatures, and automated synthetic |
| 164.312(d) | evidenced | - | Human operator authentication requires enforced MFA, and system services authenticate via private cloud identity tokens with automated secret scanning |
| 164.312(e) | evidenced | - | All services enforce HTTPS over TLS 1.3 with private service tokens and cryptographically signed data ledger checkpoints. |

## Management response and remediation (builder)
1. **164.308(a)(7) contingency (high):** a restore of the evidence node from the encrypted backup was run and verified (82 records; the restored public key equals the live key); it found a missing step (the restored key needs `chmod 600`) now in `docs/RESTORE-RUNBOOK.md`. **Correction:** a first receipt (`restore-test-1`) was recorded *before* verification succeeded, while the verifier was refusing the key mode; the ledger is append-only, so it stays; the valid receipt is `restore-test-2`.
2. **164.308(a)(1) risk analysis (high):** a risk register v1 (9 risks) was drafted and then **approved by the Human Principal on 2026-11-05**; the approval is a signed receipt in the evidence ledger (run id `policy-risk-register-v1`).
3. **164.308(a)(2) assigned responsibility:** the Human Principal is named as security-responsible person (in the facts and the register).
4. Accepted, not remediated: workforce training records (one-person team), formal workforce screening, an incident-response drill (planned).

## Trend by safeguard (round 1 → round 2 → round 3 consensus)
| Safeguard | R1 | R2 | R3 (3 runs) |
|---|---|---|---|
| 164.308(a)(1) | gap/high | partial/high | partial/medium |
| 164.308(a)(2) | partial/medium | evidenced/- | evidenced/- |
| 164.308(a)(3) | partial/medium | partial/low | partial/medium |
| 164.308(a)(4) | partial/medium | evidenced/- | evidenced/- |
| 164.308(a)(5) | gap/medium | gap/medium | gap/medium |
| 164.308(a)(6) | partial/medium | partial/medium | partial/medium |
| 164.308(a)(7) | partial/high | evidenced/- | evidenced/- |
| 164.308(a)(8) | partial/medium | partial/low | partial/low |
| 164.310(a) | inherited/- | inherited/- | inherited/- |
| 164.310(d) | partial/medium | partial/low | partial/low (runs disagreed) |
| 164.312(a) | evidenced/- | evidenced/- | evidenced/- |
| 164.312(b) | evidenced/- | evidenced/- | evidenced/- |
| 164.312(c) | evidenced/- | evidenced/- | evidenced/- |
| 164.312(d) | evidenced/- | evidenced/- | evidenced/- |
| 164.312(e) | evidenced/- | evidenced/- | evidenced/- |

Round 3 consensus: {'evidenced': 8, 'partial': 5, 'gap': 1, 'inherited': 1}.

## Residual risk (open after round 3)
- **164.308(a)(1)** (partial, medium): Formalize and record sanction policy enforcement procedures and establish scheduled, verifiable information system activity review logs.
- **164.308(a)(3)** (partial, medium): Document formal onboarding, clearance, and offboarding/termination checklists even for solo or contractor operations.
- **164.308(a)(5)** (gap, medium): Complete and record annual HIPAA security awareness training and security reminder logs.
- **164.308(a)(6)** (partial, medium): Conduct and record a tabletop incident response drill testing detection, containment, and reporting workflows.
- **164.308(a)(8)** (partial, low): Formalize the readiness assessment cadence as an annual technical and non-technical evaluation report.
- **164.310(d)** (partial, low): Implement an inventory and disposal tracking procedure for operator workstations accessing platform keys and repositories.

## Limitations (important)
- The auditor is a language model; its ratings are an input for the Human Principal, not proof, and a single run varies (see the method change above).
- Part of the pack is **stated by the operator** (system facts) and was not independently verified; the builder also wrote that file, so the auditor's independence is limited to its model family and its read-only role. Where a rating improved because of a stated fact, a human should verify the fact.
- One person operates the whole system: separation of duties is technical, not organisational.
- Wording: aligned with and mapped to the Security Rule; never "compliant" or "certified".
