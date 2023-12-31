# "NeWork"

![Icon](https://github.com/AnotherOneNewCoder/NeWork/blob/main/app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.webp)


## Дипломная работа курса Нетологии "Android-разработчик с нуля".

### Краткое описание.

В рамках дипломного проекта разработано приложение, напоминающее формат LinkedIn, в котором пользователи
могут создавать посты и события с медиаресурсами и отмечать их на Яндекс картах, указывать места работы и социальные связи (упоминание в постах, конференциях).

### Инструменты.
- Архитиктура MVVM
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
Ключевые возможности:
  - Редактирование собственного события
  - Просмотр вложенного изображения
  - Отмечать и не отмечать события, поставив "like/dislike"
  - Подписаться или отписаться от события (с автоматической отметкой в календаре)
  - Просмотр списка спикеров
  - Просмотр списка пользователей поставивших лайки
  - Просмотр списка  пользователей подписанных на событие
  - Просмотр координат события на карте
  - Делиться в других приложениях

<img src ="https://github.com/AnotherOneNewCoder/NeWork/blob/main/app/src/main/res/screenshots/Screenshot_3.jpg" width=25% height=25%> <img src ="https://github.com/AnotherOneNewCoder/NeWork/blob/main/app/src/main/res/screenshots/Screenshot_4.jpg" width=25% height=25%>

- Аутентифицированный пользователь может создавать посты с добавлением медиа-вложений (фото, аудио и видео) и координат на карте,
  редактировать и удалять их. Возможность лайкать посты других пользователей, просматривать список лайкнувших пользователей.
- Реализована возможность добавления событий с дополнительными полями (дата события, тип события (онлайн, офлайн).
- Вожможность просмотра всех зарегистрированных пользователей.
- Реализована логика просмотра профиля каждого пользователя с отображением всех его постов и мест работ.
- Получение и обработка push-уведомлений