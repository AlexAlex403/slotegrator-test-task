# slotegrator-test-task
Проект автотестов для тестового приложения

1. Для запуска UI-тестов необходим браузер Chrome (любой версии)
2. Выполнить команду:
  - для запуска всех тестов: mvn clean test
  - для запуска API-тестов:  mvn clean test -Dgroups="api"
  - для запуска UI-тестов:   mvn clean test -Dgroups="cucumber"
3. После завершения прогона автотестов отчёт откроется в браузере автоматически
