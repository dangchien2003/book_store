export const API_BASE_URL = 'http://localhost:8080/gateway/v1'

export const API_IDENTITY_SERVICE = {
  googleAuth: '/identity/auth/google/verify',
  normalAuth: '/identity/auth/sign-in',
  registerGoogle: '/identity/user/register/google',
  register: '/identity/user/sign-up'
}

export const API_PRODUCT_SERVICE = {
  getAllAuthor: '/product/author/all',
  getAllCategory: '/product/category/all',
  getAllPublisher: '/product/publisher/all',
  getAllBook: '/product/book/find'
}