import { createSlice } from '@reduxjs/toolkit'

export const filterSlice = createSlice({
  name: 'managerFilterBook',
  initialState: {
    value: {
      name: null,
      category: null,
      author: null,
      publisher: null
    }
  },
  reducers: {
    incrementByFilter: (state, action) => {
      const { field, value } = action.payload
      if (field in state.value) {
        state.value[field] = value
      }
    }
  }
})

export const { incrementByFilter } = filterSlice.actions

export default filterSlice.reducer
