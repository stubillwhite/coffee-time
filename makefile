# vim:fdm=marker

# Constants                                                                 {{{1
# ==============================================================================

SERVER_ASSETS_DIR=server/src/main/resources/web

CMDSEP=;

# Targets                                                                   {{{1
# ==============================================================================

# Help                              {{{2
# ======================================

help:
	@grep -E '^[0-9a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) \
		| sort \
		| awk 'BEGIN {FS = ":.*?## "}; {printf "$(COLOR_BLUE)%-15s$(COLOR_NONE) %s\n", $$1, $$2}'

# Clean                             {{{2
# ======================================

.PHONY: clean
clean: ## Remove all artefacts
	@echo 'Cleaning application'
	@lein clean
	@rm -f ./${APP_JAR}

# Client                            {{{2
# ======================================

.PHONY: client
client: ## Build the client
	@echo 'Building client'
	@cd client && \
		npm version patch && \
		npm run build

# Server                            {{{2
# ======================================

client-artefacts: client
	@echo 'Copying client artefacts to ${SERVER_ASSETS_DIR}'
	@rm -rf ${SERVER_ASSETS_DIR}
	@mkdir -p ${SERVER_ASSETS_DIR}
	@cp -r client/build/* ${SERVER_ASSETS_DIR} 

.PHONY: server
server: client-artefacts ## Build the server
	@echo 'Building server'
	@cd server && \
		sbt clean assembly

dist: client server ## Build the distribution

# Server                            {{{2
# ======================================

run: ## dist ## Build and run the application
	@$(eval JARFILE := $(shell ls server/target/scala-2.12/*.jar))
	@java -jar $(JARFILE) -DPORT=8080

# Deploy                            {{{2
# ======================================

deploy: dist ## Deploy the application
	@cd server && \
		sbt stage deployHeroku
