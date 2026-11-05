# Independent AI-assisted readiness assessment: HIPAA Security Rule safeguards

**Label (read first):** this is an *independent AI-assisted readiness assessment against the HIPAA Security Rule safeguards*. It is **not** a HIPAA audit, **not** a HITRUST assessment and **not** a certification. All data in the system is synthetic.

## Scope and method
- **Scope:** the synthetic-data healthcare data platform, the Entropa evidence ledger and its sites; 15 safeguards of 45 CFR 164 Subpart C (administrative, physical, technical), named by their public regulation headings.
- **Auditor:** Gemini 3.8 Flash (a different model family from the builder, which is a Claude model), read-only: it receives a data pack and has no tools. It rates each safeguard as evidenced, partial, gap, inherited or not applicable, citing the evidence it was shown.
- **Sampling:** the whole evidence ledger (round 1: 77 records; round 2: 94), summarised by control, check and outcome, plus the policy set file titles and a list of system facts stated by the operator.
- **Loop:** baseline (round 1) → builder remediates → auditor retests (round 2). The auditor never fixes or approves its own findings; the Human Principal decides.

## Round 1 (baseline): {'evidenced': 5, 'partial': 7, 'gap': 2, 'inherited': 1, 'not_applicable': 0}
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
1. **164.308(a)(7) contingency (high):** a restore of the evidence node from the encrypted backup was run and verified (82 records; restored public key equals the live key). The test found a missing step (the restored key file needs `chmod 600`); the runbook was written (`docs/RESTORE-RUNBOOK.md`). **Correction:** a first receipt (`restore-test-1`) was recorded *before* the verification had succeeded, while the verifier was refusing the key mode; the ledger is append-only, so it stays. The valid receipt is `restore-test-2`.
2. **164.308(a)(1) risk analysis (high):** a risk register v1 was drafted (9 risks, controls, likelihood, impact, residual). It is a **draft awaiting the Human Principal's approval**, so it is not yet in force.
3. **164.308(a)(2) assigned responsibility:** the Human Principal is named as the security-responsible person (stated in the facts and in the register).
4. Accepted, not remediated: workforce training records (one-person team), formal workforce screening, and an incident-response drill (planned).

## Round 2 (retest): {'evidenced': 8, 'partial': 5, 'gap': 1, 'inherited': 1, 'not_applicable': 0}
| Safeguard | Round 1 | Round 2 |
|---|---|---|
| 164.308(a)(1) | gap/high | partial/high |
| 164.308(a)(2) | partial/medium | evidenced/- |
| 164.308(a)(3) | partial/medium | partial/low |
| 164.308(a)(4) | partial/medium | evidenced/- |
| 164.308(a)(5) | gap/medium | gap/medium |
| 164.308(a)(6) | partial/medium | partial/medium |
| 164.308(a)(7) | partial/high | evidenced/- |
| 164.308(a)(8) | partial/medium | partial/low |
| 164.310(a) | inherited/- | inherited/- |
| 164.310(d) | partial/medium | partial/low |
| 164.312(a) | evidenced/- | evidenced/- |
| 164.312(b) | evidenced/- | evidenced/- |
| 164.312(c) | evidenced/- | evidenced/- |
| 164.312(d) | evidenced/- | evidenced/- |
| 164.312(e) | evidenced/- | evidenced/- |

## Residual risk (open items after the retest)
- **164.308(a)(1)** (partial, high): Formally review and approve the risk register, establish a recurring risk analysis schedule, and document system activity reviews.
- **164.308(a)(3)** (partial, low): Document lightweight workforce clearance and emergency operational succession/termination procedures tailored to the solo-operator model.
- **164.308(a)(5)** (gap, medium): Complete and record an annual security awareness and HIPAA training session for the Human Principal.
- **164.308(a)(6)** (partial, medium): Execute and document a simulated tabletop security incident response drill covering containment, analysis, and reporting.
- **164.308(a)(8)** (partial, low): Establish an annual formal review combining technical CI evaluations with non-technical administrative policy assessments.
- **164.310(d)** (partial, low): Document local workstation endpoint security, full-disk encryption verification, and media sanitization/disposal procedures.

## Limitations (important)
- The auditor is a language model. Its ratings are an input for the Human Principal, not proof.
- Part of the pack is **stated by the operator** (the system facts) and was not independently verified; the builder also wrote that file, so the auditor's independence is limited to its model family and its read-only role. Where a rating improved because of a stated fact (for example the named security officer), a human should verify the fact.
- One person operates the whole system; separation of duties is technical, not organisational.
- Wording: aligned with and mapped to the Security Rule; never "compliant" or "certified".
