---
applyTo: "src/main/java/**/*.java,src/test/java/**/*.java"
description: Testing requirements for Java changes.
---

# Testing Requirements

Apply these rules when changing Java code or proposing new features.

## Coverage Expectations

- Add or update tests for behavior changes, bug fixes, and new branches.
- Prefer tests that verify observable behavior rather than internal implementation details.
- Cover both valid input and at least one failure or edge case when practical.

## Test Design

- Keep tests small, deterministic, and easy to read.
- Use clear arrange-act-assert structure.
- Name tests based on behavior, for example `printsMessageWhenNoArgumentsProvided`.
- Avoid shared mutable state across tests.

## Scope

- Unit tests should cover parsing, validation, branching logic, and error handling.
- For command-line behavior, verify output and exit-related behavior where feasible.
- Do not add brittle tests that depend on timing, random values, or machine-specific paths unless those dependencies are controlled.

## Review Guidance

- In code review, call out missing tests whenever a change adds a branch, fixes a bug, or alters visible output.
- If tests are intentionally omitted, explain why in the response.