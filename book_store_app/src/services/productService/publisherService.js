import { API_PRODUCT_SERVICE } from '@/configs/apiConfig'
import httpClient from '@/configs/axiosConfig'

async function getPublisherInPage(page) {
  return await httpClient.get(API_PRODUCT_SERVICE.getAllPublisher, {
    params: {
      page
    }
  })
}


export {
  getPublisherInPage
}
