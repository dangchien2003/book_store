import { getPublisherInPage } from '@/services/productService/publisherService'
import { toastError } from '@/utils/toast'
import { Autocomplete, TextField } from '@mui/material'
import { useEffect, useState } from 'react'
import { useDispatch } from 'react-redux'
import { incrementByFilter } from '@/features/manager/filterBook/filterBookSlice'

const Publisher = () => {
  const [publishers, setPublishers] = useState([])
  const [page, setPage] = useState(1)
  const dispatch = useDispatch()

  useEffect(() => {
    const timeoutId = setTimeout(() => {
      getPublisherInPage(page)
        .then(response => {
          if (response.data.result.length === 0) {
            return
          }
          setPublishers(publishers.concat(response.data.result))
          setPage(currentPage => currentPage + 1)
        })
        .catch(() => {
          toastError('Có lỗi xảy ra')
        })
    }, 1500)
    return () => clearTimeout(timeoutId)
  }, [page, publishers])


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
