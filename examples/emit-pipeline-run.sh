#!/usr/bin/env bash
# Post one pipeline-run receipt to an evidence node. Synthetic example. The endpoint (POST /v1/evidence) is the
# NEXT node release; this shows the proposed request. Set NODE_URL (and TOKEN if your node is private).
set -euo pipefail
NODE_URL=${NODE_URL:-http://127.0.0.1:8080}
curl -sf -X POST "$NODE_URL/v1/evidence" \
  ${TOKEN:+-H "Authorization: Bearer $TOKEN"} \
  -H "Content-Type: application/json" \
  -d '{
    "kind": "evidence.pipeline_run",
    "label": "silver",
    "decision": "allow",
    "reason": "check_passed",
    "control": "hipaa_164_312_c1",
    "total": 7000, "passed": 6210, "flagged": 790,
    "detail": "night=2026-11-05 layer=silver valid+quarantine=input",
    "run_id": "run-2026-11-05-s"
  }'
