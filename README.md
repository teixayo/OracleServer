**OracleServer** is a ground‑up Java implementation of the Minecraft 1.8.8 server protocol. It showcases low‑level mastery of networking, packet parsing, world‑tick scheduling, and the full handshake/login/play pipeline—without relying on any existing server forks. Designed for maximum control and performance, OracleServer maintains a stable 20 TPS under load while keeping the codebase clean and modular.

## Key Features

- **Pure Java, Zero Forks**  
  Implements the official Minecraft 1.8.8 protocol end‑to‑end—no dependency on Bukkit, Spigot, Paper, or any other forks.
- **Performance‑First Architecture**  
  Custom tick loop and minimal object allocations to ensure consistent 20 TPS even under heavy player or entity load.
- **Modular Design**  
  Clean separation of networking, protocol parsing, world simulation, and plugin interface (future).
- **Work in Progress**  
  Core features are stable; api and combat mechanics and entity handling are actively under development.

## Prerequisites

- **Java 21+**  
- **Maven 3.6+**  
- **Environment Variable**  
  - `PORT` (the TCP port on which the server will listen, e.g. `25565`)

## Quick Start

1. **Clone the repository**  
   ```bash
   git clone https://github.com/teixayo/OracleServer.git
   cd OracleServer```
2. **Build the server**
   ```bash
   mvn clean package
   ```
3. **Set the port and run**
   - Linux/macOS:
      ```bash
      PORT=25565 java --enable-preview -jar target/Main-0.5.0-BETA.jar
      ```
   - Windows:
      ```
      set PORT=25565
      java --enable-preview -jar target/Main-0.5.0-BETA.jar
      ```
4. **Connect with Minecraft**
    - Launch Minecraft 1.8.8
    - Add a new server at 0.0.0.0:25565
    - Join the server
    - Run /pt to display real‑time performance metrics in your tab.

