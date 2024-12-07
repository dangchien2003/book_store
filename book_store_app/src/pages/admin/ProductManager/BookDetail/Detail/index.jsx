import { Cancel, Edit as EditIcon, Save } from '@mui/icons-material'
import { Box, Button } from '@mui/material'
import { useState } from 'react'
import Show from './Show'
import Edit from './Edit'
import { updateBookDetail } from '@/services/productService/bookService'
import { toastSuccess, toastWarning } from '@/utils/toast'


const Detail = ({ info, setInfo }) => {
  const [editing, setEditing] = useState(false)
  const [dataEdit, setDataEdit] = useState(null)
  const [hasEdit, setHasEdit] = useState(false)

  const handleToggleEdit = () => {
    const openEdit = !editing
    setEditing(openEdit)
    if (openEdit) {
      setDataEdit(info)
    } else {
      setDataEdit(null)
    }
    setHasEdit(false)
  }

  const handleClickSaveData = () => {
    if (!hasEdit) {
      toastWarning('Thông tin chưa được thay đổi')
      return
    }

    updateBookDetail(dataEdit).then(() => {
      setInfo(dataEdit)
      setHasEdit(false)
      toastSuccess('Cập nhật thành công')
    }).catch(() => { })
  }

  return (
    <Box sx={{ background: 'white', padding: '20px', borderRadius: '15px' }}>
      <Box sx={{
        paddingBottom: '15px',
        textAlign: 'right',
        ' > button': {
          margin: '0 8px',
          minWidth: '100px'
        }
      }}>
        {!editing
          ? (<Button variant="contained" endIcon={<EditIcon />} onClick={handleToggleEdit}>
            Chỉnh sửa
          </Button>)
          : (<>
            <Button variant="contained" sx={{ background: '#70f03e' }} endIcon={<Save />} onClick={handleClickSaveData}>
              Lưu
            </Button>
            <Button variant="contained" sx={{ background: '#eb4d38' }} endIcon={<Cancel />} onClick={handleToggleEdit}>
              Đóng
            </Button>
          </>)}
      </Box>
      {(!editing && info !== null) && <Show info={info} />}
      {editing && <Edit data={{ dataEdit, setDataEdit }} onEdited={setHasEdit} />}
    </Box>
  )
}

export default Detail
