import asyncio
import time

import aiohttp


async def same_customer_same_store(ex, customer_id, store_id):
    async with aiohttp.ClientSession() as session:
        # print('create task.')
        tasks = [session.get(f'http://localhost:8080/tx1/{customer_id}/{store_id}') for _ in range(1, 10**ex)]
        # print('task completed')
        result = [await t for t in tasks]
        # print('end')


async def async_main():

    for customer_id, store_id, ex in zip([10000, 10001, 10002, 10003], [1, 2, 3, 4], [1,2,3,4]):
        # print(f'test_start. iteration = {ex}')
        s = time.time()
        await same_customer_same_store(ex, customer_id, store_id)
        e = time.time()
        print(f'request count = {10**ex}. spend time = {e - s}')

asyncio.run(async_main())
