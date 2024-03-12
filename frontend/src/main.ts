import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { AppModule } from './app/app.module';

/*
  Тип запуска приложения №1: через "модуль", на который навешивается аннотация @NgModule, внутри которой
  декларируются все компоненты приложения, все роуты и так далее.
 */
platformBrowserDynamic().bootstrapModule(AppModule)
    .catch(err => console.error(err));


/*
  Тип запуска приложения №2: был при создании проекта, тут нужно сформировать объект настроек appConfig и
  передать его в этот метод, но непонятно как этот объект формировать + это походу новый способ (добавленный
  в последних версиях) => вернхий лучше
 */

// bootstrapApplication(AppComponent, appConfig)
//   .catch((err) => console.error(err));
