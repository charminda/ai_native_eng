This is a Java project. Follow these rules when generating code, changing code, or reviewing changes.

## Security And Safety

- Do not hardcode secrets, passwords, API keys, tokens, or connection strings in source code, tests, or example snippets.
- Validate external input before using it. Treat command-line arguments, environment variables, file content, and HTTP input as untrusted.
- Prefer safe defaults. Fail closed when configuration is missing or invalid.
- Avoid logging sensitive values, personal data, or internal implementation details that would create a security risk.
- When suggesting sample code, use placeholders such as `"<API_KEY>"` instead of realistic secrets.

## Java Standards

- Target simple, readable Java code. Prefer standard library APIs unless a dependency is clearly justified.
- Keep methods small and focused. Extract helper methods when a method starts mixing unrelated concerns.
- Use descriptive names. Avoid one-letter variables except for conventional loop indexes.
- Prefer immutable local variables and `final` where it improves clarity.
- Handle exceptional cases explicitly. Do not swallow exceptions.
- Preserve existing package structure and naming conventions.
- Avoid adding comments unless they clarify non-obvious intent.

## Change Discipline

- Make the smallest change that solves the problem at the root cause.
- Do not introduce speculative abstractions or framework-style patterns into a small project unless requested.
- Keep console output, error messages, and behavior deterministic where possible.
- If a change affects behavior, update or add tests when tests exist or when a test can be added with reasonable effort.

## Code Review Expectations

- Flag security issues first, especially hardcoded credentials, unsafe input handling, and overly broad exception handling.
- Flag maintainability issues such as duplicated logic, hidden side effects, and unclear naming.
- Call out missing tests for new behavior or bug fixes.