# Local 7B language model (llama.cpp)

Status: **planned, not installed on the current machine** (as of 2026-11-05). An earlier rig ran llama.cpp (Vulkan) on an RTX 3060 12 GB with a 7B instruct model (Qwen2.5-7B-Instruct, Q4_K_M) and a small embedding model (nomic-embed-text). It is reinstalled at the platinum sprint.

## Role in the design
- Optional offline demo of the English-question assistant and the RAG chat; the public chat uses Vertex AI Gemini on cloud credit.
- A policy checker in code is always on, whichever model answers. The model sees only schema and column descriptions, returns one read-only SQL query, and the checker accepts or refuses it.
- Every model call (local or cloud) is recorded as a hash-only, signed `platinum.decision` event: provider `local`, model id, token counts, input/output hashes. No prompt text, no data.
- Synthetic data only. Nothing from the model is treated as proof; findings from model reviewers are advisory.

## Install rules
User-space install under `~/.local`, working in fresh and already-open shells; model download size checked and approved first; exact commands recorded in the setup notes.
