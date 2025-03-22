JAR_FILE=target/fever-demo-miguelcabezas-0.0.1-SNAPSHOT.jar
MVN=mvn

all: build run test-e2e

build:
	$(MVN) clean package

run: build
	@echo "🚀 Running application..."
	@java -jar $(JAR_FILE) & echo $$! > app.pid
	@sleep 2
	@echo "⌛ Waiting for application to start..."

	@timeout=30; while ! curl -s http://localhost:8080 > /dev/null; do \
	    sleep 2; \
	    timeout=$$((timeout - 2)); \
	    if [ $$timeout -le 0 ]; then echo "❌ App did not start in time"; exit 1; fi; \
	done

	@echo "✅ Application running on http://localhost:8080"

clean:
	$(MVN) clean

test:
	$(MVN) test

test-e2e:
	@echo "🔍 Executing E2E tests..."
	@newman run tests/fever-test-e2e.postman_collection.json --reporters cli,junit --reporter-junit-export results.xml || { echo "❌ E2E tests failed"; exit 1; }

stop:
	@if [ -f app.pid ]; then \
	  kill `cat app.pid` && rm app.pid && echo "🛑 Application stopped"; \
	else \
	  echo "⚠️ No running application found"; \
	fi
load-test-smoke:
	@echo "🔍 Running Smoke Test..."
	@artillery run tests\load-tests-smoke.yml

load-test-constant:
	@echo "🟢 Running Constant Load Test..."
	@artillery run tests\load-tests-constant.yml

load-test-ramp:
	@echo "🔥 Running Ramp Load Test..."
	@artillery run tests\load-tests-ramp.yml

.PHONY: all build run clean test test-e2e stop load-test-smoke load-test-constant load-test-ramp