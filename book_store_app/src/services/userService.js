import { API_IDENTITY_SERVICE } from '@/configs/apiConfig'
import httpClient from '@/configs/axiosConfig'

export async function register(email, password) {
  return await httpClient.post(API_IDENTITY_SERVICE.register, {
    email,
    password
  })
}

export async function registerByGoogle(authorizationCode, codeVerifier) {
  return await httpClient.post(API_IDENTITY_SERVICE.registerGoogle, {
    authorizationCode,
    codeVerifier
  })
}