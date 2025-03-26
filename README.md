![Screenshots](Screenshots/NewsCastMbAppScreenshot.png)

# NewsCastMB

**NewsCastMB** is a feature-rich news application built to demonstrate Modern Android Development best practices and the latest tools.

## 🛠 Built With

This app is developed using:

- **Android Views (XML)** for UI design.
- **Room** database for offline news caching.
- **Paging 3** library for efficient pagination and large data handling.
- **Retrofit** for making network requests.
- **Dagger Hilt** for dependency injection.
- **Navigation Component** for seamless fragment navigation.
- **Coil** for efficient image loading.
- **Kotlin Flows & Coroutines** for reactive UI.
- **PreferenceSettingCompat** for managing app settings.

## 🚀 Features  

![Screenshots](Screenshots/NewsCastDarkScreenshot.png)  

Key features of **NewsCastMB**:

- ✅ Offline article caching using Paging 3 Remote Mediator.
- ✅ Bookmark articles for offline reading using Room.
- ✅ Multiple news categories with a tab layout.
- ✅ Edge-to-edge UI, drawing behind the system bars.
- ✅ Light & Dark theme support using PreferenceSettingCompat.
- ✅ Search functionality to find specific articles.
- ✅ Swipe to delete for bookmarked news and more...

## 👀 Note  

> **Before you begin**

This app uses the **NewsAPI** service to fetch articles. To display news, you need to [register an account](https://newsapi.org/register), get an API key, and include it in **`AppConstants.API_KEY`**.

## 🔧 Improvements  

This app is built with **Android Views** and relies on an external news API, which is not free.  
For a more advanced version, check out **[WikiNewsFeed](https://github.com/MubarakNative/WikiNewsFeed)**—built with **Jetpack Compose** and powered by the free **WikiNews API**.
