"""Post an evidence receipt to a node (standard library only). Synthetic example; endpoint: next node release."""
import json
import os
import urllib.request


def emit(kind: str, label: str, decision: str, reason: str, control: str, total: int, passed: int, flagged: int,
         detail: str, run_id: str) -> bytes:
    body = {"kind": kind, "label": label, "decision": decision, "reason": reason, "control": control,
            "total": total, "passed": passed, "flagged": flagged, "detail": detail, "run_id": run_id}
    headers = {"Content-Type": "application/json"}
    if os.environ.get("TOKEN"):
        headers["Authorization"] = "Bearer " + os.environ["TOKEN"]
    url = os.environ.get("NODE_URL", "http://127.0.0.1:8080") + "/v1/evidence"
    req = urllib.request.Request(url, data=json.dumps(body).encode(), headers=headers, method="POST")
    with urllib.request.urlopen(req, timeout=15) as resp:
        return resp.read()


if __name__ == "__main__":
    # a row-level-security check: 25 probes as the wrong user, all returned zero rows
    print(emit("evidence.control_check", "rls_test", "allow", "check_passed", "hipaa_164_312_a1",
               total=25, passed=25, flagged=0, detail="wrong-actor queries returned zero rows", run_id="run-rls-1"))
