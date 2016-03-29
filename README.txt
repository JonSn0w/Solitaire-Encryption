Solitaire Encryption


This program performs Bruce Schneier's Solitaire encryption/decryption on the user specified input and returns the generated text. This process involves the use of a "deck" or a specifically ordered list of the numbers 1-28, used in collaboration with a generated keystream to encrypt/decrypt a message. Without this, an encrypted message will be unable to be properly decrypted. As each value of the generated keystream and the provided deck are used to encrypt/decrypt the cooresponding letter of the user's message.

More info about the Solitaire algorithm: https://en.wikipedia.org/wiki/Solitaire_(cipher)


____________________________________Command-Line Parameters____________________________________

	Param #1:
		"e" --> to specify encryption
		"d" --> to specify decryption

	Param #2:
		filename --> specify the text file containing the deck to be used by the program.
				  ( ie: "deck.txt" )