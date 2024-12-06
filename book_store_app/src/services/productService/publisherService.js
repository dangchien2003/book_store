import { API_PRODUCT_SERVICE } from '@/configs/apiConfig'
import httpClient from '@/configs/axiosConfig'

export async function getPublisherInPage(page) {
  return await httpClient.get(API_PRODUCT_SERVICE.getAllPublisher, {
    params: {
      page
    }
  })
}


export async function getPublisherBtId(id) {
  return await httpClient.get(API_PRODUCT_SERVICE.getInfoPublisher, {
    params: {
      id: id
    }
  })
}
