module Main where

import Test.HUnit
import System.Exit

--finds the fibonacci numbers which are the previous two fibonacci numbers added together
fibonacci :: Int -> Int
fibonacci 0 = 0
fibonacci 1 = 1
fibonacci n = fibonacci(n-1) + fibonacci(n-2)

--multiplies every element in the list together
myProduct :: [Integer] -> Integer
myProduct [] = 1
myProduct (n:ns) = n * myProduct ns

--makes a list of lists into a single list
flatten :: [[a]] -> [a]
flatten [xs] = xs
flatten (x:xs) = x ++ flatten xs

--adds 1 for every element in the list
myLength :: [a] -> Int
myLength [] = 0
myLength (x:xs) = 1 + myLength xs

--sorts the list based on whether the element is larger or smaller than the key
quicksort :: Ord t => [t] -> [t]
quicksort [] = []
quicksort (x:xs) = quicksort ys ++ [x] ++ quicksort zs
                   where
                      ys = [a| a <- xs, a <= x]
                      zs = [b| b <- xs, b > x]

--checks if a number is an element of the list
isElement :: Eq a => a -> [a] -> Bool
isElement _ [] = False
isElement n (x:xs) = if n == x then True else isElement n xs

--filters out the even numbers in a list of lists using generators and the modular function
removeEven :: [[Integer]] -> [[Integer]]
removeEven [[]] = [[]]
removeEven xss = [[x]| xs <- xss, x <- xs, (x `mod` 2) /= 0]

isort :: [Int] -> [Int]
isort = undefined --no need to do this one

--grabs the front element element of both lists and creates a list where the elements are intermixed
riffle :: [a] -> [a] -> [a]
riffle [] _ = []
riffle _ [] = []
riffle (x:xs) (y:ys) = [x] ++ [y] ++ riffle xs ys

--recursively riffles n times after separating the list given into two even sublists using prelude functions take and drop
shuffle :: Int -> [a] -> [a]
shuffle 0 xs = xs
shuffle n xs = shuffle (n-1) (riffle (take ((length xs) `div` 2) xs) (drop ((length xs)`div` 2) xs))

factors :: Int -> [Int]
factors n = [x | x <- [1..n], n `mod` x == 0, x /= n] --gets the factors of an element excluding itself

perfects:: Int -> [Int]
perfects n = [x| x <- [1..n], sum(factors x) == x]

replicate' :: Int -> a -> [a]
replicate' 0 _ = []
replicate' n a = [a| _ <- [1..n]]

myTestList = 
  TestList [ 
    "fibonacci" ~: fibonacci 4 ~=? 3

    , "myProduct" ~: myProduct [1..10] ~=? 3628800
    
    , "flatten 1" ~: flatten [[]::[Int]] ~=? []
    , "flatten 2" ~: flatten [[]::[Int], [], []] ~=? []
    , "flatten 3" ~: flatten [[1], [2, 3, 4], [], [5, 6]] ~=? [1, 2, 3, 4, 5, 6]
      
    , "myLength" ~: myLength [1, 2, 3] ~=? 3

    , "quicksort 1" ~: quicksort [3, 2, 5, 1, 6] ~=? [1,2,3,5,6]
    , "quicksort 2" ~: quicksort "howdy" ~=? "dhowy"
    
    , "isElement 1" ~: (isElement 'c' "abcd") ~=? True
    , "isElement 2" ~: (isElement 'e' "abcd") ~=? False
      
   
    ]

main = do c <- runTestTT myTestList
          putStrLn $ show c
          let errs = errors c
              fails = failures c
          exitWith (codeGet errs fails)
          
codeGet errs fails
 | fails > 0       = ExitFailure 2
 | errs > 0        = ExitFailure 1
 | otherwise       = ExitSuccess

