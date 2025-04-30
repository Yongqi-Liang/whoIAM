[中](../README.md) | En

_Registration No.: Soft Copyright 15220256_

<div style="text-align: center;">
    <img src="../logo.png" alt="Logo" style="width: 100px; height: auto;">
    <p style="font-size: 20px;font-weight: bold">whoIAM - A Multi-Factor Authentication & On-Premises Deployable Enterprise Identity Management Platform</p>
    <p align="center">
      <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white" />
      <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white" />
      <img src="https://img.shields.io/badge/Vue.js-35495E?style=for-the-badge&logo=vuedotjs&logoColor=4FC08D" />
      <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white" />
      <img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white" />
      <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" />
    </p>
</div>
<div style="text-align: right;">
	<i>
        -- by Yongqi Liang
    </i>
</div>


#### 📖 Product Introduction

**whoIAM** is an enterprise-grade digital identity management platform that supports **Multi-Factor Authentication (MFA)** and **on-premises deployment**.  
It ensures secure access to enterprise applications through flexible OTP authentication mechanisms, adapting to both local and hybrid cloud scenarios to meet basic identity management needs.

#### 📦 Deployment Guide

###### Backend (Server module)

1. **Configure environment:**
   - Edit `application-dev.properties` and `application-prod.properties` under `server/src/main/resources` with your database, Redis, and other connection info.
   - Set the active profile in `application.properties`, for example:
     ```properties
     spring.profiles.active=dev
     ```

2. **Start the backend:**
   - Run the main class (e.g., `ServerApplication`) directly in IntelliJ IDEA.

###### Frontend (webapp module)

1. **Install dependencies:**
   ```bash
   cd webapp
   npm install

2. **Run in local development environment:**

   ```bash
   npm run serve:dev
   ```

3. **Build for production:**

   ```bash
   npm run build:prod
   ```

###### Documentation (Doc module)

- The `doc` module uses WriterSide to generate the API documentation.
- You can view it directly or edit it further as needed.

###### Mobile Examples (Authenticator & Privileger modules)

- `authenticator` and `privileger` are Android Framework-based sample apps demonstrating mobile authentication scenarios.
- Open the respective modules in Android Studio to build and deploy to your test devices.

#### 🤝 How to Contribute

We welcome contributions from developers to improve whoIAM! ✨

- **Submit Issues:** Report bugs or suggest improvements and feature ideas.
- **Fork the Repository:** Add features or fix issues and submit a Pull Request.
- **Improve Documentation:** Help enhance and expand documentation content.
- **Mobile Extensions:** Expand the sample mobile authentication apps.

> Before contributing, please read [CONTRIBUTING.md](./CONTRIBUTING.md) or engage in discussions via the Issue section.



