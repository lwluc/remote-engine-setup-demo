# Remote Engine Setup Demo

This repo should provide a starting point how to apply the [remote engine pattern](https://camunda.com/blog/2022/02/moving-from-embedded-to-remote-workflow-engines/).


# Table of Contents

* [✨Features](#features)
  * [⚙️ Camunda Platform](#camunda-platform)
  * [🔨 Camunda Worker](#camunda-worker)
    * [📤Auto Deployment](#auto-deployment)
* [🚀Getting Started](#getting-started)

# ✨Features

## ⚙️Camunda Platform

[Remote Engine Module](./remote-engine)

Have a look at the running instance: [Camunda Platform](http://localhost:8080)

You could login with `username: admin` and `password: pw`.

##  🔨Camunda Worker

[Worker Module](./worker)

Using the [external task pattern](https://docs.camunda.org/manual/7.20/user-guide/process-engine/external-tasks/) to 
work on a service task.

### 📤Auto Deployment

The [artefact-deployment](./artefactdeployment) module provides a custom annotation which searches all `.bpmn`, 
`.dmn` and `.form` files in your resources and deployed them to the remote engines via the REST Api.

# 🚀Getting Started

```shell
# Install all dependencies
$ ./mvnw install

# Install and start the remote engine
$ ./mvnw spring-boot:run -pl remote-engine

# Start the external task worker
$ ./mvnw spring-boot:run -pl worker
```