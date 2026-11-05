# AWS checkpoint witness (multi-cloud)

A second independent witness runs on **AWS Lambda**, beside the Azure one, so the AI-decision ledger's checkpoints are co-signed from two clouds other than the one the ledger signs on (Google Cloud).

## How it is built
- The same `entropa-witnessd` program as on Azure, built with the `aws` feature and packaged as a Lambda function (custom runtime `provided.al2023`, with the AWS Lambda Web Adapter layer) behind a function URL.
- **Signing:** an AWS KMS ML-DSA-65 key. The private key never leaves KMS; the function may only call `Sign`, `GetPublicKey` and `DescribeKey` on that one key.
- **State:** a few bytes per log in a private S3 bucket, replaced with conditional writes (`If-Match` / `If-None-Match`) so two writers cannot both win. Equivocation evidence is one small object per event, capped at 100.
- **Access:** the function URL is public, but the witness only co-signs a checkpoint whose log signature verifies and whose consistency proof links it to the last one it saw. Forks and rollbacks are refused and logged. Errors never echo the request.
- **Least privilege:** the execution role is `entropa-py-witness-exec` with a permissions boundary; the deploy ran as a scoped, MFA-gated role, not as root.

## Cost
Lambda and S3 are inside the free tier at witness volumes (a handful of requests per export). The KMS key was already in use for the ledger work (about 1 USD a month, plus 0.15 USD per 10,000 signatures).

## What was found while building it (and fixed)
1. A build on a newer glibc needed **glibc 2.38**; Lambda's runtime has 2.34, so the function would not have started. A small C shim, compiled by the build script, supplies the two C23 functions in terms of the classic ones.
2. The ledger's post-quantum public key is about 3.9 KB, more than Lambda's **4 KB environment limit**; the witness's log list is now a file shipped inside the package (`WITNESS_LOGS_FILE`).
3. Public function URLs need **two** permission statements (`InvokeFunctionUrl` and `InvokeFunction` via the URL); with one, the URL answers 403.

## Limit
All three ledgers (AI decisions, evidence, council) sign with one key, and a witness tracks one log per key. Today the AI-decision ledger is witnessed by both clouds; the other two need their own signing keys.

## Check it
The AI Audit Explorer's "Verify in your browser" button reports the independent witness statements it checked (two witness keys).
