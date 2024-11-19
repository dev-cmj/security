/*
 * 해당 util 내용
 * 현재 시간 기준 으로
 * date, date + time 가져 오는 역할을 한다.
 * 또한 format 형식과 format 형식 없이 문자열( 'toString' 뒤에 붙힘)만 가져 오는 기능을 한다.
 * */
const now = new Date();

const getTimeStamp = () => {
  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, "0");
  const day = String(now.getDate()).padStart(2, "0");
  const hours = String(now.getHours()).padStart(2, "0");
  const minutes = String(now.getMinutes()).padStart(2, "0");
  const seconds = String(now.getSeconds()).padStart(2, "0");

  return {
    year,
    month,
    day,
    hours,
    minutes,
    seconds,
  };
};

const getCurrentTimestamp = () => {
  let current = getTimeStamp();
  return `${current.year}-${current.month}-${current.day} ${current.hours}:${current.minutes}:${current.seconds}`;
};

const getCurrentTimestampToString = () => {
  let current = getTimeStamp();
  return `${current.year}${current.month}${current.day}${current.hours}${current.minutes}${current.seconds}`;
};

const getCurrentDate = () => {
  let current = getTimeStamp();
  return `${current.year}-${current.month}-${current.day}`;
};

const getCurrentDateToString = () => {
  let current = getTimeStamp();
  return `${current.year}${current.month}${current.day}`;
};

export default {
  getCurrentTimestamp,
  getCurrentTimestampToString,
  getCurrentDate,
  getCurrentDateToString,
};
