# 🛒 Mart Wagon (마트 왜건)

> **나만의 스마트한 장바구니 가격 비교 & 통계 다이어리**  
> "내가 샀던 제품의 가격을 기록해두고, 추후 비슷한 제품을 구매할 때 최저가인지 한눈에 비교해 보세요!" [1]

![PlayStore Badge](https://img.shields.io/badge/Google_Play-414141?style=for-the-badge&logo=google-play&logoColor=white) <!-- 출시 후 링크 연결 -->

## 📖 프로젝트 개요
Mart Wagon은 일상적인 마트 장보기에서 발생하는 '가격 비교'의 번거로움을 해결하기 위해 기획된 안드로이드 애플리케이션입니다 [1]. 사용자는 제품의 가격을 기록하고, 통계 탭의 자체 비교 로직을 통해 가장 저렴한 마트와 물품을 직관적으로 확인할 수 있습니다 [1]. 

단순한 기능 구현을 넘어, **100% Jetpack Compose와 Multi-Module Clean Architecture를 기반으로 설계하여 높은 유지보수성과 확장성을 자랑하는 모던 안드로이드(Modern Android) 프로젝트**입니다 [1].

- **개발 기간:** 2026.04 ~ 2026.05 [1]
- **개발 인원:** 1인 개발 (기획, 디자인, 클라이언트 개발 전담)

<br>

## 🛠 Tech Stack
- **Language:** Kotlin
- **Architecture:** MVVM, Multi-Module, Clean Architecture [1]
- **UI:** Jetpack Compose (100%), Navigation Compose (Nav3) [1]
- **Asynchronous & Reactive:** Coroutines, Flow [1]
- **DI (Dependency Injection):** Dagger-Hilt [1]
- **Local Storage:** RoomDB, DataStore [1, 4]

<br>

## 🏗 Architecture & Multi-Module Strategy

본 프로젝트는 기능 수정이나 확장 시 발생하는 사이드 이펙트를 최소화하고, 관심사를 철저히 분리하기 위해 **Multi-Module 아키텍처**를 채택했습니다 [1].

```text
📦 프로젝트 구조
 ┣ 📂 app (Hilt Application 및 의존성 그래프 조립)
 ┣ 📂 core
 ┃  ┣ 📂 common (공통 UI, Theme, Util 로직)
 ┃  ┣ 📂 data (Repository 구현체, API/DB 통신)
 ┃  ┣ 📂 database (RoomDB, DAO)
 ┃  ┣ 📂 datastore (Local Preference)
 ┃  ┗ 📂 domain (Model, Repository Interface, UseCase)
 ┗ 📂 feature
    ┣ 📂 home (기록 검색)
    ┣ 📂 record (기록 추가)
    ┣ 📂 report (가격 비교 및 통계)
    ┗ 📂 setting (앱 설정)
    ┗ 📂 tag (기록 모음)


