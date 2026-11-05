# Risk register v1: APPROVED by the Human Principal on 2026-11-05 (his words in chat: "I approve RISK-REGISTER-v1")

Scope: the synthetic-data healthcare data platform, the Entropa ledger and its sites. Likelihood and impact: L/M/H. Review quarterly (policy P7).

| # | Asset | Threat | Existing controls | L | I | Residual | Owner |
|---|---|---|---|---|---|---|---|
| R1 | Ledger signing keys (GCP KMS, AWS KMS) | Misuse of a cloud identity that can sign | Managed keys, no key export, least-privilege roles, MFA on every human account, audit logs | L | H | Medium | Principal |
| R2 | Evidence node key and ledgers | Loss or corruption | Encrypted mirrors (rclone crypt, private Git), restore tested 2026-11-05 (runbook), append-only verification | M | M | Low | Principal |
| R3 | Real data entering the system by mistake | PHI appears in prompts, logs or public pages | Synthetic-only rule (P0), reserved invalid identifier ranges, canary scans, identifier-shape refusal in the ledger, pre-commit and CI scans | L | H | Medium | Principal |
| R4 | AI agents acting beyond scope | Destructive or outward actions without approval | Command hook that denies destructive actions, approvals gate, human decides hard-rule items, council review | M | H | Medium | Principal |
| R5 | Prompt injection through data a model reads | A model follows instructions hidden in data | Static rules (data is evidence, not instructions), SQL guard allow-list, guard tests, no model has write access | M | M | Low | Principal |
| R6 | Single operator | Key person risk, no independent oversight | Separate technical identities per role, signed record of every step, witnesses on other clouds, independent AI review; honest limit: not organisational independence | H | M | Medium (accepted) | Principal |
| R7 | Cloud provider outage or credit expiry | Sites or ledger unavailable | Static sites on a CDN, scale-to-zero node, witnesses on two clouds, Azure is temporary (ends 2026-11-16) | M | M | Low | Principal |
| R8 | Supply chain | Malicious or vulnerable dependency | Locked dependencies, cargo-audit and deny, secret scanning, per-module pipelines, SHA-pinned CI actions | M | M | Low | Principal |
| R9 | Public wording overclaims | "compliant" or "certified" used without basis | Wording rule and scans: "aligned with / mapped to", never compliant or certified | L | M | Low | Principal |

Open items from the 2026-11-05 assessment: workforce training record (one-person team: not applicable, stated), incident-response drill (planned), formal designation of the security responsibility (this register names the Principal).
