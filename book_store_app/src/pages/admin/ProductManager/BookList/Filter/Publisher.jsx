import { Autocomplete, TextField } from '@mui/material'
import { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { incrementByFilter } from '@/features/manager/filterBook/filterBookSlice'
import { getAllPublisher } from '@/features/publisher/publisherSlice'

const Publisher = () => {
  const { publishers, done } = useSelector((state) => state.publisher)
  const dispatch = useDispatch()

  useEffect(() => {
    if (!done) {
      dispatch(getAllPublisher())
    }
  }, [done, dispatch])

  const handleChangeValue = (event, newValue) => {

    const value = newValue ? newValue.id : null
    dispatch(incrementByFilter({ field: 'publisher', value: value }))
  }

  return (
    <Autocomplete
      disablePortal
      options={publishers}
      sx={{ mt: '15px', background: 'white' }}
      getOptionLabel={(option) => option.name}
      onChange={handleChangeValue}
      renderInput={(params) => <TextField {...params} label="Nhà xuất bản" variant="outlined" />}
    />
  )
}

export default Publisher
