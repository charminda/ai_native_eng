---
applyTo: "src/main/java/**/*.java"
description: Architecture patterns and design constraints for Java source files.
---

# Architecture Patterns

Use these guidelines when adding new Java classes or restructuring existing ones.

## Separation Of Concerns

- Keep the entry point thin. `main` methods should delegate work instead of containing most business logic.
- Separate input handling, business logic, and output formatting into distinct methods or classes when the code grows.
- Avoid mixing configuration lookup, validation, execution, and presentation in one method.

## Dependencies And Coupling

- Prefer constructor or method parameter injection over hidden global state.
- Avoid static mutable state unless there is a clear, justified reason.
- Keep dependencies pointed inward: utility code should not depend on application entry points.

## State Management

- Prefer stateless services and immutable data where practical.
- If state is required, keep ownership clear and limit the mutation surface.
- Avoid sharing mutable collections across unrelated code paths.

## Error Handling

- Validate early and return clear errors.
- Catch exceptions only when the code can add context, recover safely, or translate them into a better boundary-level error.
- Do not use exceptions for normal control flow.

## Growth Path

- For small features, prefer private helper methods before introducing extra classes.
- When complexity increases, extract cohesive classes such as argument parsers, services, or formatters.
- Keep public APIs narrow and intention-revealing.