# Linux test matrix (2026-11-05)

Every command block of https://docs.entropa.space/install-linux was run, as written, on **fresh** cloud images in qemu/KVM (2 GB RAM, 2 vCPU), non-GUI server images only:

| Family | Image | Package manager |
|---|---|---|
| apt | Ubuntu 24.04.5 LTS (`noble-server-cloudimg-amd64`, 621 MB) | apt |
| dnf | Rocky Linux 10.2 (`Rocky-10-GenericCloud-Base`, 545 MB) | dnf |

Fedora itself, Debian and other releases are **not** tested here (same package managers).

## What each step installs
Counts are packages added to the fresh image (Ubuntu starts with 664, Rocky with 443).

| Step | Ubuntu 24.04.5 | Rocky 10.2 |
|---|---|---|
| B. curl, jq, git (installed only if missing) | 0 (all present) | 67 (git 2.52.0 and its dependencies, jq, oniguruma; curl was present) |
| C. container route (optional) | Docker: 8 packages (`docker.io` 29.1.3, `containerd` 2.2.1, `runc` 1.3.4) | Podman: 18 packages (`podman` 5.8.2, `crun` 1.30.1, `conmon` 2.2.1) |
| D. Java 17 + sbt (only for your own Scala/Spark pipeline) | 0 system packages: Temurin 17.0.21 and sbt are installed into the home folder with Coursier (about 1.3 GB under `~/.cache/coursier`) | same: 0 system packages |
| E. Rust toolchain (only to build the open-core crates) | 51 packages (`build-essential`, `gcc` 13.3, `make` 4.3, `binutils` 2.42), then rustup installs cargo 1.99.1 in the home folder | 9 packages (`gcc` 14.3.1, `make` 4.4.1, `binutils` 2.41), then cargo 1.99.1 |

Versions seen: Java `openjdk version "17.0.21"` (Temurin), sbt runner 2.0.12 (each project pins its own sbt version in `project/build.properties`), cargo 1.99.1.

## Node program tests (both distros pass)
Release build, needs only glibc 2.34 or newer (both images have 2.39); no Java, Scala or Rust at run time.

- create a node (`init`), record an AI decision, an audit-evidence event and a council step; verify all three ledgers (`OK records=1 checkpoints=1` each)
- serve over HTTP (`/healthz`, `/v1/pubkey`, `POST /v1/council`, `GET /v1/council/verify` returned `OK records=2 checkpoints=2`)
- witness node (`entropa-witnessd`) started with the node's public key; the node's export then carried **1 witness statement** on both distros
- open-core repository: `cargo test --workspace` passed on both (13 + 42 + 46 + 5 = 106 tests, 0 failed)

## Bugs this test found (all fixed in the docs)
1. `moby-engine` is a Fedora package and does not exist on Rocky/RHEL: the dnf route now uses **Podman**.
2. `java-17-openjdk-devel` does not exist on Rocky 10 (only Java 21 and 25): a failed install fell through to Coursier, which installed **Java 21**. The Java block now installs Temurin **17** with Coursier on both families.
3. `cargo install entropa-node` cannot work: the crates.io `entropa-node` is a library. The docs now say so.
4. Not fixed yet: the node CLI prints a panic message when its output pipe is closed early (for example `| head`). Harmless, to be made quiet.

## Node release 0.2.0 (node-v0.2.0) tested from the published download
The tarball was downloaded with the documented commands on fresh Ubuntu 24.04.5 and Rocky Linux 10.2 images; `sha256sum -c` passed; then `init`, `log`, `verify` (`OK records=1 checkpoints=1`), `serve` over HTTP and the witness program all worked. The binaries need glibc 2.34 or newer and no Java, Scala or Rust. The build strips symbols and rewrites build paths; the only "personal-looking" string inside is "Scottsdale", from a public TLS root certificate.
