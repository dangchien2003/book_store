import { createSlice } from '@reduxjs/toolkit'

export const managerClickFindBook = createSlice({
  name: 'managerClickFindBook',
  initialState: {
    value: false
  },
  reducers: {
    setFinding: (state) => {
      state.value = true
    },
    reset: (state) => {
      state.value = false
    }
  }
})

export const { setFinding, reset } = managerClickFindBook.actions

export const incrementWithTimeout = () => (dispatch) => {
  dispatch(setFinding())
  setTimeout(() => {
    dispatch(reset())
  }, 1000)
}

export default managerClickFindBook.reducer