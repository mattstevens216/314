pyths :: Int -> [(Int, Int, Int)]
pyths n = [(x,y,z)| x<-[1..n],
                    y<-[1..n],
                    z<-[1..n],
                    x^2 + y^2 == z^2]

scalarproduct :: [Int] -> [Int] -> Int
scalarproduct xs ys = sum[x*y|(x,y)<- zip xs ys]

sum' :: [Int] -> Int
sum' [] = 0
sum' (x:xs) = x + sum' xs 
