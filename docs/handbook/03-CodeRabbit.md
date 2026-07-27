# CodeRabbit Handbook

## Overview

CodeRabbit is an AI-powered code review tool that integrates with GitHub Pull Requests.

---

## Topics Completed

- GitHub App Installation
- Repository Integration
- Minimal YAML Configuration
- Draft Review Configuration
- Incremental Reviews
- High-Level Review Summary
- Markdown Path Instructions
- Java Path Instructions
- Test Automation Path Instructions

---

## Current YAML Features

### Auto Review

Automatically reviews Pull Requests.

---

### Draft Reviews

Skips reviewing Draft Pull Requests.

---

### Incremental Reviews

Reviews only new commits after the previous review.

---

### High-Level Summary

Generates an executive summary of the Pull Request.

---

### Path Instructions

Different review guidance for:

- Markdown files
- Java source code
- Test automation code

---

## Maven Review Rules

CodeRabbit reviews `pom.xml` for:

- Duplicate dependencies
- Version conflicts
- Missing dependency versions
- Unused dependencies
- Proper plugin configuration
- Dependency scope correctness
- Dependency version consistency
- Explicit plugin versions
- Transitive dependencies
- Maintainable build configuration

## Git Ignore Review

CodeRabbit reviews `.gitignore` for:

- Missing ignore rules
- Duplicate patterns
- IDE artifacts
- Build output
- Sensitive files
- Repository cleanliness

## Selenium Test Review

CodeRabbit reviews Selenium automation code for:

- Test readability
- Page Object Model usage
- Explicit waits
- Assertion quality
- Reusable utilities
- Maintainable test flow

## Learning Workflow

Official Documentation

↓

Implementation

↓

Commit

↓

Push

↓

Pull Request

↓

Validation

---

## Next Topics

- Maven Review Rules
- GitHub Repository Rules
- Selenium Framework Rules
- API Automation Rules