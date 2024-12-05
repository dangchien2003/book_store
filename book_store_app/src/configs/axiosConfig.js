import axios from 'axios'
import { API_BASE_URL, API_IDENTITY_SERVICE } from '@/configs/apiConfig'
import { getAccessToken, getRefeshToken, setAccessToken } from '@/services/localStorageService'

let refreshing = false

const refreshToken = async () => {
  refreshing = true
  const response = await axios.post(`${API_BASE_URL}` + API_IDENTITY_SERVICE.refreshToken,
    {
      refreshToken: getRefeshToken(),
      accessToken: getAccessToken()
    },
    {
      headers: {
        Authorization: undefined
      }
    })


  if (response.status === 200) {
    const newAccessToken = response.data.result.token
    setAccessToken(newAccessToken)
  }

  refreshing = false
}

const httpClient = axios.create({
  baseURL: `${API_BASE_URL}`,
  headers: {
    'Content-Type': 'application/json'
  },
  timeout: 5000
})

httpClient.interceptors.request.use(
  (config) => {
    const token = getAccessToken()
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

httpClient.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config
    const status = error.response?.status
    const code = error.response?.data?.code

    if (status === 401 && code === 1041 && !originalRequest._retry) {

      originalRequest._retry = true
      try {
        if (!refreshing) {
          await refreshToken()
        }

        await waitForRefreshing()

        originalRequest.headers['Authorization'] = 'Bearer ' + getAccessToken()

        return httpClient(originalRequest)
      } catch (refreshError) {
        window.location.href = '/login'
      }
    }

    return Promise.reject(error)
  }
)

const waitForRefreshing = () => {
  return new Promise((resolve) => {
    const interval = setInterval(() => {
      if (!refreshing) {
        clearInterval(interval)
        resolve()
      }
    }, 100)
  })
}


export default httpClient