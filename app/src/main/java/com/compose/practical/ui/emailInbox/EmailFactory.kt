package com.compose.practical.ui.emailInbox

object EmailFactory {
    fun makeEmailList(): List<Email> {
        return listOf(
            Email(
                "1",
                "Did you get my email?",
                "Hey, I just wanted to check that you got my last email " +
                        "- I know you're pretty busy these days!"
            ),
            Email(
                "2",
                "Welcome!",
                "Thanks for signing up to our mailing list. " +
                        "You\'ll need to confirm your email address to receive future emails from us."
            ),
            Email(
                "3",
                "Thanks for your order!",
                "Your order is on its way! Keep an eye on its progress using our tracking system."
            ),
            Email(
                "4",
                "Join our team? :)",
                "Thanks for spending the time to interview with us over the last few weeks - we'd love to invite you to join our team!"
            ),
            Email(
                "5",
                "RE: Coffee",
                "Was great to bump into your last week - I'd love to catch-up properly, maybe we could meet at the weekend? There's a lovely new coffee bar on my street!"
            ),
            Email(
                "6",
                "You didn't win this time",
                "Thanks for entering our competition. Unfortunately you didn't win this time! Please try again soon, you might have better luck in future :)"
            )
        )
    }
}
