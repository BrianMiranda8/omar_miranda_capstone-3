export default defineNuxtRouteMiddleware((to, from) => {


    const userCookie = useCookie('user')
    if (!userCookie.value) {
        return navigateTo('/')
    }
})
