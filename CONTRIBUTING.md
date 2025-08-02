# Contributing to BountyPlus

Thanks for your interest in contributing! This project thrives on community involvement, so clear guidelines keep development smooth and consistent.

---

## ✅ 1. Code of Conduct
- Be respectful in all discussions and pull requests.
- Assume good intentions — we’re here to collaborate, not compete.
- No harassment, hate speech, or toxic behavior.

---

## 🏗 2. Development Guidelines
- **Language & API:**
    - Use **Java 21+** (or the version stated in the repo).
    - Follow the **Spigot API** best practices. Avoid NMS unless explicitly discussed in an issue.
- **Style:**
    - Stick to the project’s existing **code style** (indentation, naming, brackets).
    - Use meaningful commit messages (`feat: add player bounty GUI` vs. `update stuff`).
- **Documentation:**
    - All new features **must include Javadoc** where appropriate.
    - If you change plugin behavior, **update the README** or wiki.

---

## 🔄 3. Contribution Process
1. **Fork** the repository and create a feature branch:

   ```bash
   git checkout -b feature/your-feature-name
   ```

2. **Open an Issue** before starting major changes.
    - Use issues for **feature proposals**, **bug reports**, or **discussion**.
    - Small typo fixes or formatting improvements don’t need prior approval.

3. **Submit a Pull Request (PR):**
    - Reference any related issue (\`Fixes #12\`).
    - Provide a **clear description** of what the PR does.
    - Keep PRs **focused** (1 feature/fix per PR).

---

## 🧪 4. Testing Expectations
- Test your changes on a **local Spigot server** before submitting.
- Ensure the plugin:
    - **Loads without errors**
    - **Does not break existing features**
- If applicable, add tests or a test plan for reviewers.

---

## 🚩 5. Bounty-Specific Rules
- **Economy/permissions integrations** must be optional & configurable.
- Avoid adding hard dependencies (Vault, PlaceholderAPI, etc.) unless discussed.
- Config options **must default to safe, non-intrusive values**.

---

## ⚖️ 6. Licensing
- All contributions are released under the **same license as the project** (e.g., MIT/GPL).
- By submitting code, you confirm you **own the rights** to it and are willing to have it included.

---

## 🏆 7. Recognition
- Significant contributors will be **credited in the README** and/or plugin.yml.
- First-time contributors are encouraged to add themselves to a CONTRIBUTORS.md file.

---

## ❌ 8. What *Not* to Submit
- Obfuscated or malicious code
- Paid/premium resource links
- Features unrelated to the bounty system (keep the scope focused)

---

Thanks for helping make **BountyPlus** better! 🎉