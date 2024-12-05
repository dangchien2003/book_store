import { API_IDENTITY_SERVICE } from '@/configs/apiConfig'
import httpClient from '@/configs/axiosConfig'
export async function logoutAccount(access, refresh) {
  return await httpClient.post(API_IDENTITY_SERVICE.logout, {
    refreshToken: refresh,
    accessToken: access
  }, {
    headers: {
      Authorization: undefined
    }
  })
}