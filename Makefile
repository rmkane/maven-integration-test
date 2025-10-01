# ============================================================================
# Makefile based on: https://cloud.theodo.com/en/blog/beautiful-makefile-awk
# ============================================================================

JVM_ARGS = -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8787
API_URL  = http://localhost:8080

.DEFAULT_GOAL := install

.PHONY: install package verify compile run debug test clean format checkstyle \
        dependency-tree spring-boot-image curl help

##@ Build
compile: ## Compile source code
	@mvn compile

package: ## Package the application JAR/WAR
	@mvn package

install: ## Install the project to local Maven repo (skip tests)
	@mvn install -q -DskipTests

clean: ## Clean build artifacts
	@mvn clean

verify: ## Run integration tests and verify the project
	@mvn verify

##@ Run
run: ## Run the Spring Boot app
	@mvn spring-boot:run

debug: ## Run with remote debugging enabled
	@mvn spring-boot:run -Dspring-boot.run.jvmArguments="$(JVM_ARGS)"

spring-boot-image: ## Build a container image with Spring Boot plugin
	@mvn spring-boot:build-image

##@ Test
test: ## Run unit tests
	@mvn test

##@ Quality
format: ## Apply code formatting (Spotless or equivalent)
	@mvn spotless:apply

checkstyle: ## Run Checkstyle analysis
	@mvn checkstyle:check

dependency-tree: ## Show Maven dependency tree
	@mvn dependency:tree

##@ Utilities
status: ## Check application status
	@curl -v -X GET $(API_URL)/actuator/health

##@ Info
help: ## Display this help
	@awk 'BEGIN { \
			FS = ":.*##"; \
		 	printf "\nUsage:\n  make \033[36m<target>\033[0m\n" \
		} \
		/^[a-zA-Z_0-9-]+:.*?##/ { \
			printf "  \033[36m%-20s\033[0m %s\n", $$1, $$2 \
		} \
		/^##@/ { \
			printf "\n\033[1m%s\033[0m\n", substr($$0, 5) \
	     } ' $(MAKEFILE_LIST)
