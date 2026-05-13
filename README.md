# 🛒 Mart Wagon (마트 왜건)

> **나만의 스마트한 장바구니 가격 비교 & 통계 다이어리**  
> "내가 샀던 제품의 가격을 기록해두고, 추후 비슷한 제품을 구매할 때 최저가인지 한눈에 비교해 보세요!"

![PlayStore Badge](https://www.naver.com)

## 📖 프로젝트 개요
Mart Wagon은 일상적인 마트 장보기에서 발생하는 '가격 비교'의 번거로움을 해결하기 위해 기획된 안드로이드 애플리케이션입니다. 사용자는 제품의 가격을 기록하고, 통계 탭의 자체 비교 로직을 통해 가장 저렴한 마트와 물품을 직관적으로 확인할 수 있습니다. 

단순한 기능 구현을 넘어, **100% Jetpack Compose와 Multi-Module Clean Architecture를 기반으로 설계하여 높은 유지보수성과 확장성을 자랑하는 모던 안드로이드(Modern Android) 프로젝트**입니다.

- **개발 기간:** 2026.04 ~ 2026.05
- **개발 인원:** 1인 개발 (기획, 디자인, 클라이언트 개발 전담)

<br>

## 🛠 Tech Stack
- **Language:** Kotlin
- **Architecture:** MVVM, Multi-Module, Clean Architecture
- **UI:** Jetpack Compose (100%), Navigation Compose (Nav3)
- **Asynchronous & Reactive:** Coroutines, Flow
- **DI (Dependency Injection):** Dagger-Hilt
- **Local Storage:** RoomDB, DataStore

<br>

## 🏗 Architecture & Multi-Module Strategy

본 프로젝트는 기능 수정이나 확장 시 발생하는 사이드 이펙트를 최소화하고, 관심사를 철저히 분리하기 위해 **Multi-Module 아키텍처**를 채택했습니다.

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
```

💡 Core Architecture Highlights

Domain Layer와 UseCase를 통한 비즈니스 로직 격리: UI가 데이터를 직접 가공하지 않도록 GetMartComparisonUseCase 등 세분화된 UseCase를 구현했습니다. 

Data 모듈은 데이터를 가져오는 역할만, UseCase는 비교/정렬 등 비즈니스 로직만 처리하며, Feature(UI)는 결과값만 구독하도록 결합도를 낮췄습니다.

RoomDB + Flow 기반의 Reactive UI: RoomDB의 데이터를 Flow 형식으로 반환받아 데이터의 변화를 실시간으로 관찰(Observe)하고, 변경 사항이 즉각적으로 Compose UI에 반영되도록 반응형 앱을 구현했습니다.

🤔 Decision Log & Troubleshooting
1. 왜 100% Jetpack Compose와 Nav3를 선택했는가?
 기존 XML 방식의 복잡한 RecyclerView + Adapter + ViewHolder 구조에서 벗어나, 선언형 UI의 이점을 극대화하기 위해 전면 Compose를 도입했습니다. Navigation Compose(Nav3)를 결합하여 복잡한 화면 전환 상태를 단일 진실 공급원(SSOT)으로 관리하며 생산성을 크게 높였습니다.

3. Common UI 모듈 분리로 재사용성 200% 향상
앱 전반에서 반복적으로 사용되는 Dialog, ModalBottomSheet, TextField 등의 컴포넌트를 core:common 모듈로 분리했습니다. 특히 CommonDialog의 경우, 내부 컨텐츠를 동적으로 주입받을 수 있도록 @Composable (Modifier) -> Unit 형태의 Slot API 패턴을 적용하여 UI의 일관성을 유지하면서도 뛰어난 확장성을 확보했습니다.

