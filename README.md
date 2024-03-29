# "NeWork"

![Icon](https://github.com/AnotherOneNewCoder/NeWork/blob/main/app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.webp)


## Дипломная работа курса Нетологии "Android-разработчик с нуля".

### Краткое описание.

В рамках дипломного проекта разработано приложение, напоминающее формат LinkedIn, в котором пользователи
могут создавать посты и события с медиаресурсами и отмечать их на Яндекс картах, указывать места работы и социальные связи (упоминание в постах, конференциях).
Ссылка на сервер - https://netomedia.ru/swagger/?format=openapi

### Инструменты.
- Архитектура MVVM
- Библиотеки:
    - Material Design
    - ROOM
    - OKHTTP
    - Retrofit
    - Hilt
    - LiveData, Flow
    - Coroutines
    - YandexMapsMobile
    - ImagePicker, Glide
    - Exoplayer

### Функционал приложения.

- **Регистрация и аутентификация пользователей.**

Пользователи могут ввести свои логин и пароль для входа в аккаунт или создать новую учетную запись, указав имя, будующий логин и пароль для входа.

<img src ="https://github.com/AnotherOneNewCoder/NeWork/blob/main/app/src/main/res/screenshots/Screenshot_1.jpg" width=25% height=25%> <img src ="https://github.com/AnotherOneNewCoder/NeWork/blob/main/app/src/main/res/screenshots/Screenshot_2.jpg" width=25% height=25%>

- **Страница ленты событий.**

Здесь отображается список созданных событий всех аутентифицированных пользователей.
Ключевые возможности данной страницы:
  - Редактирование собственных событий.
  - Просмотр вложенного изображения.
  - Возможность отмечать и не отмечать события, поставив "like/dislike".
  - Возможность подписаться или отписаться от события (с автоматической отметкой в календаре).
  - Просмотр списка спикеров.
  - Просмотр списка поставивших лайки пользователей.
  - Просмотр списка  подписанных на событие пользователей.
  - Просмотр координат события на карте.
  - Возможность делиться контентом данной страницы в других приложениях.

<img src ="https://github.com/AnotherOneNewCoder/NeWork/blob/main/app/src/main/res/screenshots/Screenshot_3.jpg" width=25% height=25%> <img src ="https://github.com/AnotherOneNewCoder/NeWork/blob/main/app/src/main/res/screenshots/Screenshot_4.jpg" width=25% height=25%>

- **Страница ленты постов.**

Здесь отображается список созданных постов всех аутентифицированных пользователей.
Ключевые возможности данной страницы:
- Редактирование собственных постов.
- Просмотр вложенных файлов (изображений, аудио, видео).
- Возможность отмечать и не отмечать события, поставив "like/dislike".
- Просмотр списка отмеченных пользователей.
- Просмотр списка поставивших лайки пользователей.
- Просмотр координат поста на карте.
- Возможность делиться контентом данной страницы в других приложениях.

<img src ="https://github.com/AnotherOneNewCoder/NeWork/blob/main/app/src/main/res/screenshots/Screenshot_5.jpg" width=25% height=25%> <img src ="https://github.com/AnotherOneNewCoder/NeWork/blob/main/app/src/main/res/screenshots/Screenshot_6.jpg" width=25% height=25%>

- **Страница списка пользователей (участников)**

Страница, на которой пользователи могут просматривать и искать других членов сообщества.
Ключевые возможности данной страницы:
- Поиск пользователей по имени и id.
- Возможность перехода на профиль пользователя с отображением всех его созданных событий, постов и мест работ.

<img src ="https://github.com/AnotherOneNewCoder/NeWork/blob/main/app/src/main/res/screenshots/Screenshot_7.jpg" width=25% height=25%> <img src ="https://github.com/AnotherOneNewCoder/NeWork/blob/main/app/src/main/res/screenshots/Screenshot_8.jpg" width=25% height=25%><img src ="https://github.com/AnotherOneNewCoder/NeWork/blob/main/app/src/main/res/screenshots/Screenshot_9.jpg" width=25% height=25%> 

- **Страница профиля пользователя**

Страница, на которой пользователи могут просматривать информацию о своем профиле, включая свои события, посты, места работ и записи в календаре.
Ключевые возможности данной страницы:
- Возможность создать новый пост, событие, вакансию и редактировать их.
- Возможность добавлять вложения (изображение, аудио, видео).

<img src ="https://github.com/AnotherOneNewCoder/NeWork/blob/main/app/src/main/res/screenshots/Screenshot_profile.png" width=25% height=25%> <img src ="https://github.com/AnotherOneNewCoder/NeWork/blob/main/app/src/main/res/screenshots/Screenshot_profile_2.png" width=25% height=25%>

