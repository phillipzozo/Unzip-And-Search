# Unzip-And-Search
A simple tool for scanning files with multiple filter conditions then tell you where them are, and also it can decompressing them.

## Multiple filters
You can use multiple [Linux's Regular Expressions](https://en.wikipedia.org/wiki/Regular_expression) to scanning files, such as following example:
* *.pdf
* [0-9]*
* my*

## Supported decompression format
These formats are based  on [Apache Commons Compress](https://commons.apache.org/proper/commons-compress/).
* 7z
* lzma
* xz
* z
* zst
* tar
* zip
* bz2
* gz
* pack200

## How to use?
The following will briefly explain the operation procedures

### Step 1

Open **`Unzip_And_Search_execute.jar`** then it will create two folders **`config`** & **`results`**.

There will have two files "compressed_format.txt" & "illegal_format.txt" in "config" folder, they are used to store the filter conditions you entered last time.

And "results" folder will store all the results you have been scanned.

![image](https://user-images.githubusercontent.com/51452722/135711238-f2bfe0d4-37c7-4746-9038-f4c7124badaa.png)

![image](https://user-images.githubusercontent.com/51452722/135715797-3a2cea11-f2c6-4de8-b775-54f7fe9aa422.png)

### Step 2

* There hava 3 ways to put a compressed file or folder in, and then click next.
  * Drop and drag it to this tool.
  * Click middle button to select.
  * Enter it's path.

![image](https://user-images.githubusercontent.com/51452722/135717317-7afa0acf-43d5-43e8-b13b-a3600412e71a.png)

![image](https://user-images.githubusercontent.com/51452722/135717533-2113c579-8aa4-4095-8bf5-d93699012037.png)

### Step 3

You can enter your conditions here for scan compressed files, and also you can decompress them automatically by checkbox **`Auto decompressed`**

* Checked
<br/>![image](https://user-images.githubusercontent.com/51452722/135718084-9e0c550a-54cc-4dc6-8fb7-a0b7335c78f4.png)

* Unchecked
<br/>![image](https://user-images.githubusercontent.com/51452722/135718161-8ac0b84b-c6f4-439f-8772-8e4c7163a879.png)

![image](https://user-images.githubusercontent.com/51452722/135717741-6bdf9f87-0801-4444-a22d-0119d8a21bda.png)

### Step 4

You can enter your conditions here for scan other files that you don't want to see.

![image](https://user-images.githubusercontent.com/51452722/135718321-92fe3389-8ed7-444c-99d1-185e636c9d59.png)

### Step 5

Here will decompressed and scan files, and will output the process in the middle textarea.

![image](https://user-images.githubusercontent.com/51452722/135718486-cf718630-73ab-41d3-8925-986138e1fea9.png)

### Step 6 (Completed!)

#### Congratulations!

You can see the result simply, and you can click **`Open Report`** to open result's file window.

![image](https://user-images.githubusercontent.com/51452722/135718618-de807d20-fe17-4156-95f6-d58d7a1a8005.png)

* All the files in the result will probably look like this:
  * `source`: Files from source compressed file or folder.
  * `result_compressed.txt`: Paths of the compressed files found.
  * `result_decompressed.txt`: Paths of decompressed files.
  * `result_illegal.txt`: Paths of the illegal files found.
  * `result_log.txt`: Process log(**Step 5**).

![image](https://user-images.githubusercontent.com/51452722/135719619-414b6db4-a58f-4f86-bf10-af822b51563b.png)

