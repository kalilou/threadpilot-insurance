
```diff
 ____             _       _   ____              _
/ ___| _ __  _ __(_)_ __ | |_| __ )  ___   ___ | |_
\___ \| '_ \| '__| | '_ \| __|  _ \ / _ \ / _ \| __|
 ___) | |_) | |  | | | | | |_| |_) | (_) | (_) | |_
|____/| .__/|_|  |_|_| |_|\__|____/ \___/ \___/ \__|
 _____|_|                       _       _ _       _
|_   _| |__  _ __ ___  __ _  __| |_ __ (_) | ___ | |_
  | | | '_ \| '__/ _ \/ _` |/ _` | '_ \| | |/ _ \| __|
  | | | | | | | |  __/ (_| | (_| | |_) | | | (_) | |_
  |_| |_| |_|_|  \___|\__,_|\__,_| .__/|_|_|\___/ \__|
                                 |_|
```
# Threadpilot Microservices Architecture

## Overview
This project contains two microservices:
- **Vehicle Service** (`threadpilot-vehicle`)
- **Insurance Service** (`threadpilot-insurance`)

## Architecture Diagram

![Architecture Diagram](/images/architecture_all.png)

---

- **Vehicle Service** exposes endpoints to get vehicle details by registration number and by owner personal ID.
- **Insurance Service** exposes an endpoint to get insurance information by personal ID.

> For more details, see the individual service READMEs.