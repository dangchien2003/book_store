import { API_IDENTITY_SERVICE } from '@/configs/apiConfig'
import httpClient from '@/configs/axiosConfig'

async function googleAuthentication(authorizationCode, codeVerifier) {
  return await httpClient.post(API_IDENTITY_SERVICE.googleAuth, {
    authorizationCode,
    codeVerifier
  })
}

async function normalAuthentication(email, password) {
  return await httpClient.post(API_IDENTITY_SERVICE.normalAuth, {
    email,
    password
  })
}

export {
  googleAuthentication,
  normalAuthentication
}
