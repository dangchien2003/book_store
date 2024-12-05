const googleCodeVerifierkey = 'googleCodeVerifier'
const refeshTokenkey = 'refresh'
const accessTokenKey = 'access'

export function setCodeVerifierToLocalStorage(value) {
  localStorage.setItem(googleCodeVerifierkey, value)
}

export function getCodeVerifierToLocalStorage() {
  return localStorage.getItem(googleCodeVerifierkey)
}

export function setRefeshToken(token) {
  localStorage.setItem(refeshTokenkey, token)
}

export function getRefeshToken() {
  return localStorage.getItem(refeshTokenkey)
}

export function deleteRefeshToken() {
  return localStorage.removeItem(refeshTokenkey)
}


export function setAccessToken(value) {
  localStorage.setItem(accessTokenKey, value)
}

export function getAccessToken() {
  return localStorage.getItem(accessTokenKey)
}

export function deleteAccessToken() {
  return localStorage.removeItem(accessTokenKey)
}

