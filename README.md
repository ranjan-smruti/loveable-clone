# Lovable Clone Project

A full-stack AI-powered application platform inspired by **Lovable**,
designed to let users create and manage projects, generate application
code through AI conversations, inspect project files, run live previews,
collaborate with other members, and manage usage and subscriptions.

## Overview

The Lovable Clone Project combines project management, authentication,
AI-assisted code generation, file management, live application previews,
collaboration, billing, and quota controls into a single platform.

The system is organized around a **project-centric workflow**:

1.  A user signs up or logs in.
2.  The user creates and manages projects.
3.  The user starts an AI chat session for a project.
4.  Prompts are sent to the AI code-generation service through a
    streaming chat API.
5.  Generated/updated files become available through the project file
    APIs.
6.  The project can be launched as a live preview.
7.  Preview status and runtime logs can be monitored.
8.  Users can download project files as a ZIP archive.
9.  Projects can be shared with other users through member roles and
    permissions.
10. Usage quotas and subscription limits are enforced according to the
    user's plan.

## Key Features

### Authentication

-   User signup
-   User login
-   Retrieve authenticated user profile
-   Session/authentication handling through protected APIs

### Project Management

-   Create projects
-   View a list of projects
-   Read project details
-   Update project information
-   Delete projects
-   Support multiple members per project

### AI Code Generation

-   Create AI chat sessions
-   List existing chat sessions
-   Load complete chat history
-   Stream AI responses
-   Retry failed generation requests
-   Generate and modify project code through conversational interaction

### File Management

-   Retrieve project file tree
-   Retrieve file metadata
-   Read individual file contents
-   Download individual files
-   Download an entire project as a ZIP archive

### Live Preview & Code Runner

-   Start a project preview
-   Return preview URL and preview status
-   Track preview lifecycle:
    -   `CREATING`
    -   `RUNNING`
    -   `FAILED`
-   Stream preview/runtime logs
-   Monitor operations such as dependency installation and Vite HMR
-   Stop and clean up running previews

### Collaboration & Permissions

-   View project members
-   Invite members by email
-   Change member roles
-   Remove members
-   Allow one project to be associated with multiple users

### Subscription & Billing

-   Support `FREE` and `PRO` plans
-   Stripe Checkout integration
-   Stripe Customer Portal integration
-   View current subscription
-   Track next billing information
-   Enforce plan-based usage limits

### Usage & Quotas

The platform can track limits such as:

-   AI tokens used
-   Projects created
-   Running previews
-   Plan-specific limits

Additional infrastructure can include:

-   Redis-based rate limiting
-   Zipkin distributed tracing

## API Reference

The project is organized around REST APIs, with Server-Sent Events (SSE)
used for streaming operations.

### Authentication

  Operation             Method   Endpoint
  --------------------- -------- --------------------
  Login                 POST     `/api/auth/login`
  Signup                POST     `/api/auth/signup`
  Get current profile   GET      `/api/auth/me`

### Projects

  -----------------------------------------------------------------------
  Operation               Method                  Endpoint
  ----------------------- ----------------------- -----------------------
  Create / Read / Update  CRUD                    `/api/projects/{id}`
  / Delete project                                

  Get all projects        GET                     `/api/projects`
  -----------------------------------------------------------------------

### Files

  Operation                    Method   Endpoint
  ---------------------------- -------- -----------------------------------
  Get file tree + metadata     GET      `/api/projects/{id}/files`
  Get/download a single file   GET      `/api/projects/{id}/files/**`
  Download project ZIP         GET      `/api/projects/{id}/download-zip`

### Sharing & Permissions

  Operation                Method   Endpoint
  ------------------------ -------- ---------------------------------------
  Get project members      GET      `/api/projects/{id}/members`
  Invite member by email   POST     `/api/projects/{id}/members`
  Change member role       PATCH    `/api/projects/{id}/members/{userId}`
  Remove member            DELETE   `/api/projects/{id}/members/{userId}`

### Subscription & Billing

  Operation                        Method   Endpoint
  -------------------------------- -------- ------------------------
  List plans                       GET      `/api/plans`
  Get current subscription         GET      `/api/me/subscription`
  Create Stripe Checkout session   POST     `/api/stripe/checkout`
  Open Stripe Customer Portal      POST     `/api/stripe/portal`

### Usage & Quotas

  Operation                 Method   Endpoint
  ------------------------- -------- ---------------------
  Get today's usage         GET      `/api/usage/today`
  Get current plan limits   GET      `/api/usage/limits`

### Chat & AI Generation

  -------------------------------------------------------------------------------------------
  Operation               Method                  Endpoint
  ----------------------- ----------------------- -------------------------------------------
  List project chat       GET                     `/api/projects/{id}/chat-sessions`
  sessions                                        

  Create chat session     POST                    `/api/projects/{id}/chat-sessions`

  Get chat history        GET                     `/api/chat/sessions/{sessionId}/messages`

  Stream AI chat          POST + SSE              `/api/chat/stream`
  -------------------------------------------------------------------------------------------

### Preview & Runner

  Operation             Method   Endpoint
  --------------------- -------- ------------------------------------
  Start live preview    POST     `/api/projects/{id}/preview`
  Get preview status    GET      `/api/previews/{previewId}/status`
  Stream preview logs   SSE      `/api/previews/{previewId}/logs`
  Stop/delete preview   DELETE   `/api/previews/{previewId}`

## System Architecture

The system is composed of several logical layers:

``` text
                    ┌──────────────────────┐
                    │      Web Client      │
                    │  UI / Project IDE   │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │      API Layer       │
                    │ REST APIs + SSE      │
                    └──────────┬───────────┘
                               │
             ┌─────────────────┼─────────────────┐
             ▼                 ▼                 ▼
      ┌────────────┐    ┌─────────────┐   ┌──────────────┐
      │   Auth &   │    │  Project &  │   │ Subscription │
      │   Users    │    │    Files    │   │   & Usage    │
      └────────────┘    └─────────────┘   └──────────────┘
             │                 │                 │
             └─────────────────┼─────────────────┘
                               ▼
                    ┌──────────────────────┐
                    │ AI / Code Generation │
                    │      Service         │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │ Preview / Runner     │
                    │ npm + Vite + HMR     │
                    └──────────────────────┘
```

The project documentation also includes dedicated architecture diagrams
for the AI design, code-execution system, and database/entity
relationships.

## Data Model

At a high level, the application revolves around these entities:

-   **User** --- authentication and account information.
-   **Project** --- an application/workspace owned by or shared with
    users.
-   **Project Member** --- associates users with projects and their
    roles.
-   **Chat Session** --- an AI conversation associated with a project.
-   **Chat Message** --- individual user/AI messages in a chat session.
-   **Project File** --- source code and project assets.
-   **Preview** --- a running or previously executed project instance.
-   **Subscription** --- user's current billing plan.
-   **Usage** --- quota consumption such as tokens, projects, and
    previews.

The supplied project package contains an ER diagram that can be used as
the detailed database reference.

## Typical User Flow

``` text
Sign Up / Login
      │
      ▼
Create Project
      │
      ▼
Start AI Chat Session
      │
      ▼
Describe Application
      │
      ▼
AI Generates / Updates Code
      │
      ▼
Inspect Project Files
      │
      ▼
Start Live Preview
      │
      ├──────────────► View Preview
      │
      └──────────────► Monitor Logs
      │
      ▼
Download / Continue Editing / Share
```

## Streaming

The system uses **Server-Sent Events (SSE)** for operations where the
client benefits from receiving incremental updates.

Primary streaming use cases include:

-   AI chat/code-generation responses
-   Live preview logs
-   Runtime/dependency installation logs
-   Vite HMR-related preview activity

This allows the UI to display progress without waiting for the complete
operation to finish.

## Plans

The documented plans are:

  Plan   Description
  ------ ----------------------------------------
  FREE   Base plan with defined usage limits
  PRO    Paid plan with higher/different limits

Exact quota values should be configured through the application's
subscription/usage configuration rather than hard-coded in this README.

## Security Considerations

For a production deployment, the following areas should be addressed:

-   Secure password handling and authentication
-   Authorization checks on every project/file/member operation
-   Project-level access control
-   Role-based permissions for collaborators
-   Secure handling of AI/provider credentials
-   Stripe webhook signature validation
-   Input validation and request-size limits
-   Rate limiting
-   Isolation of code execution environments
-   Protection against arbitrary command execution
-   Secure file-path handling to prevent path traversal
-   Logging and distributed tracing without exposing secrets
-   Secure handling of environment variables and API keys

> **Important:** A system that executes generated application code
> should run workloads in isolated, restricted environments with
> appropriate CPU, memory, filesystem, network, and execution-time
> limits.

## Observability

The architecture supports distributed tracing through **Zipkin**.

Recommended operational metrics include:

-   API latency
-   AI generation latency
-   AI generation failures
-   Preview creation time
-   Preview failures
-   Running preview count
-   Token consumption
-   Quota exhaustion
-   Authentication failures
-   Code execution failures
-   SSE connection health

## Repository Documentation Assets

The supplied project documentation package includes:

-   Lovable Clone Project feature documentation
-   Core API documentation
-   ER diagram
-   AI design architecture diagram
-   Code execution system architecture diagram

These assets provide the functional and architectural reference for
implementing the platform.

## API Design Notes

The API design follows a resource-oriented structure:

-   `/api/auth/*` for authentication
-   `/api/projects/*` for project resources
-   `/api/chat/*` for AI conversations
-   `/api/previews/*` for preview lifecycle management
-   `/api/plans` and `/api/me/subscription` for billing
-   `/api/usage/*` for quotas and consumption

For streaming endpoints, clients should handle connection interruptions
and reconnect where appropriate. AI generation should also expose a
retry path for failed requests.

## Future Enhancements

Potential extensions for the project include:

-   GitHub/GitLab repository integration
-   Version history and rollback
-   Branch-based project editing
-   Automatic project backups
-   Team-level organizations
-   Fine-grained RBAC
-   Custom AI model/provider selection
-   Multi-language project templates
-   Automated testing before preview deployment
-   Production deployment workflows
-   Domain/custom URL management
-   AI generation cost analytics
-   Audit logs
-   Notification system
-   More granular usage dashboards

Replace this section with the actual license before publishing the
repository.
