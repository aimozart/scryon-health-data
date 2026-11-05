# Restore runbook: evidence node (tested 2026-11-05)

1. `rclone copy gcrypt:entropa-evidence-node ~/restore-dir`  (the crypt remote decrypts; needs the rclone config)
2. **`chmod 600 ~/restore-dir/platinum.key`**  (rclone does not keep the mode; the node refuses a key readable by others. Found by the first restore test.)
3. `entropa-platinum verify-evidence ~/restore-dir`  must print `OK records=N checkpoints=N`.
4. `entropa-platinum pubkey ~/restore-dir` must equal the live node's public key.
5. Only then point the node at the restored directory.

Test history: restore-test-1 (receipt recorded in error before verification: verify had refused the key mode); restore-test-2 passed after the permission step.
