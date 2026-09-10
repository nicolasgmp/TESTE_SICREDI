import http from 'k6/http';
import { check } from 'k6';

export const options = {
  vus: 100,
  duration: '2m',
};

const BASE_URL = __ENV.BASE_URL || 'http://localhost:8080';
const AGENDA_ID = Number(__ENV.AGENDA_ID || 1);

export default function () {
  const associateId = __VU * 1000000 + __ITER;

  const payload = JSON.stringify({
    agendaId: AGENDA_ID,
    associateId: associateId,
    type: 'YES'
  });

  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  const response = http.post(
    `${BASE_URL}/v1/vote`,
    payload,
    params
  );

  check(response, {
    'status is 200': (r) => r.status === 200,
  });
}

