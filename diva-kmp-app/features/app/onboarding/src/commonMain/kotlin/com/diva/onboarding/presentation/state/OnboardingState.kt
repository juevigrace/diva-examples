package com.diva.onboarding.presentation.state


data class OnboardingState(
    val page: Int = 0,
    val pages: List<OnboardingPage> = defaultPages,
    val currentPage: OnboardingPage = pages[page],
    val previousEnabled: Boolean = page > 0,
    val nextEnabled: Boolean = page < pages.lastIndex,
    val showBottomRow: Boolean = page != pages.lastIndex,
    val showNavigateToAuth: Boolean = page == pages.lastIndex
) {
    companion object {
        private val defaultPages: List<OnboardingPage> = listOf(
            OnboardingPage(
                title = "Connect with Social & Media",
                description = "Share moments, follow friends, and discover trending content from around the world."
            ),
            OnboardingPage(
                title = "Personalize Your Profile",
                description = "Customize your profile with photos, bio, and unique style to express yourself."
            ),
            OnboardingPage(
                title = "Chat & Interact",
                description = "Message friends, join groups, and stay connected anytime, anywhere."
            ),
            OnboardingPage(
                title = "Get Started",
                description = "Ready to explore all features? Sign in to begin your journey!"
            )
        )
    }
}

data class OnboardingPage(
    val title: String,
    val description: String
)
