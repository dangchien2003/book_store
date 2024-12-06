import { getPublisherInPage } from '@/services/productService/publisherService'
import { sleep } from '@/utils/promise'
import { createSlice } from '@reduxjs/toolkit'

const initialState = {
  publishers: [],
  done: false
}

export const publisher = createSlice({
  name: 'publisher',
  initialState,
  reducers: {
    addPublisher: (state, action) => {
      state.publishers = [...state.publishers, ...action.payload]
    },
    setDone: (state, action) => {
      state.done = action.payload
    }
  }
})

export const { addPublisher, setDone } = publisher.actions

export const getAllPublisher = () => async (dispatch, getState) => {
  const { done } = getState().publisher

  if (done) {
    return
  }

  let page = 1
  try {
    // eslint-disable-next-line no-constant-condition
    while (true) {
      const response = await getPublisherInPage(page)

      if (response.data.result.length === 0) {
        dispatch(setDone(true))
        break
      }

      dispatch(addPublisher(response.data.result))
      page += 1

      await sleep(500)
    }
  } catch (error) {
    dispatch(setDone(true))
  }
}

export default publisher.reducer
