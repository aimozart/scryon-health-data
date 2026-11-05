# Azure checkpoint witness (multi-cloud, temporary)

An independent witness co-signs the ledger's signed checkpoints so a log cannot silently fork or roll back. The ledger signs with Google Cloud KMS (ML-DSA-65); the witness runs on **Azure Container Apps** with its own key, and the same service is portable to AWS.

## What runs
- `entropa-witnessd`: POST /v1/cosign, GET /v1/pubkey. It accepts a checkpoint only with a valid log signature and a valid consistency proof from the last size it saw (compare-and-swap state on an Azure Files share). Fork or rollback is refused and logged as equivocation evidence. Fails closed; echoes nothing.
- The node asks the witness after each export and keeps the signed statements; bundles carry them and the in-browser verifier checks each statement's signature and that its root and size match a checkpoint in the same bundle.

## Cost and lifetime
Free-trial credit, spending limit on, about 2 to 3 USD until the credit expires on 2026-11-16. Resource group `entropa-witness-rg` (registry Basic, storage + 1 GB share, Container Apps env, app scaling 0 to 1). The image is built locally and pushed (ACR Tasks is blocked on free trials). Teardown is deleting the resource group, done by the owner only. After expiry the durable witness is AWS.

## Evidence
Deploys and statements are recorded as signed ledger events. Subscription and tenant identifiers are not published.
