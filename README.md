# JetpackerNews

A HackerNews clone built for Android in the Jetpack Compose framework & toolset.

## Design

This is my first attempt at a Jetpack Compose application. Ideally the goal was an Android application that feels
as good as I feel a good Android application feeling after being an Android user for just about a decade. So I
tried my best to ensure that general expectations work.

I liked the choice to support full user-chosen themability in Material 3/You, so I decided to go ahead with using
the `androidx.compose.material3:material3` alpha dependency for most UI elements and components.

I required Google's Accompanist component collection for certain features which I would argue are nearly mandatory for 
an Android application. Those requirements were routing transitions and pull-to-refresh, which are features I've reasonably 
come to expect as a long-term Android user, and thus didn't want to not have in this application.

I also ended up needing to use an `AndroidView` component for rendering HTML, as HackerNews comments can contain HTML and
Jetpack Compose seemingly has no current defacto method of parsing HTML.

## Structure

I structured this very similarly as I would a small React app seeing as the component and state modeling are so
similar in Compose applications. As such, most logic and UI models are in the `io.standel.jetpackernews.components`
package. I also tried to lean towards using ViewModel classes as opposed to component local state, due to the 
higher-level lifespan of ViewModel data and state-survivability between navigations.